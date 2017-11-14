package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.event.base.CollectionEventType;
import com.github.bordertech.wcomponents.task.TaskFuture;
import com.github.bordertech.wcomponents.task.TaskManager;
import com.github.bordertech.wcomponents.task.TaskManagerFactory;
import com.github.bordertech.wcomponents.task.service.ResultHolder;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceException;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Default async store.
 *
 * @param <K> the key type
 * @param <V> the entry type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultAsyncStore<K, V> extends DefaultStore implements KeyValueAsyncStore<K, V> {

	private static final Log LOG = LogFactory.getLog(DefaultAsyncStore.class);

	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

	private final ServiceAction<K, V> serviceAction;

	public DefaultAsyncStore(final ServiceAction<K, V> serviceAction, final StoreType storeType) {
		this(serviceAction, storeType, null);
	}

	public DefaultAsyncStore(final ServiceAction<K, V> serviceAction, final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
		this.serviceAction = serviceAction;
	}

	@Override
	public void registerListeners() {
		// Collection Listeners
		for (CollectionEventType type : CollectionEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleListEvents(event);
				}
			};
			registerListener(type, listener);
		}
	}

	@Override
	public ServiceStatus getStatus(final K key) throws StoreException {
		checkStatus(key);
		// Check for result
		ResultHolder<V> holder = getResultHolder(key);
		if (holder != null) {
			return holder.hasException() ? ServiceStatus.ERROR : ServiceStatus.COMPLETE;
		}
		//Check if processing
		TaskFuture<ResultHolder> future = getFuture(key);
		return future == null ? ServiceStatus.NOT_STARTED : ServiceStatus.STARTED;
	}

	@Override
	public V getValue(final K key) throws StoreException {

		checkStatus(key);

		// Check if have result
		ResultHolder<V> holder = getResultHolder(key);
		if (holder != null) {
			if (holder.hasException()) {
				throw new StoreException(holder.getException().getMessage(), holder.getException());
			} else {
				return holder.getResult();
			}
		}
		throw new StoreException("No value for this key.");
	}

	protected void handleListEvents(final Event event) {
		CollectionEventType type = (CollectionEventType) event.getEventKey().getEventType();
		boolean handled = true;
		switch (type) {
			case RESET_ITEMS:
				handleResetListEvent((K) event.getData());
				break;
			case LOAD_ITEMS:
				handleLoadItemsEvent((K) event.getData());
				break;
			case ADD_ITEM:
				handleAddItemEvent((Map.Entry<K, V>) event.getData());
				break;
			case REMOVE_ITEM:
				handleRemoveItemEvent((Map.Entry<K, V>) event.getData());
				break;
			case UPDATE_ITEM:
				handleUpdateItemEvent((Map.Entry<K, V>) event.getData());
				break;

			default:
				handled = false;
		}
		if (handled) {
			dispatchChangeEvent();
		}

	}

	protected void handleResetListEvent(final K key) {
		clearFuture(key);
		clearResult(key);
	}

	protected void handleAddItemEvent(final Map.Entry<K, V> item) {
		K key = item.getKey();
		V value = item.getValue();
		clearFuture(key);
		clearResult(key);
		setResultHolder(key, new ResultHolder(value));
	}

	protected void handleRemoveItemEvent(final Map.Entry<K, V> item) {
		K key = item.getKey();
		clearFuture(key);
		clearResult(key);
	}

	protected void handleUpdateItemEvent(final Map.Entry<K, V> item) {
		K key = item.getKey();
		V value = item.getValue();
		clearFuture(key);
		clearResult(key);
		setResultHolder(key, new ResultHolder(value));
	}

	protected void handleLoadItemsEvent(final K key) {
		handleAsyncPollingAction(key);
	}

	protected void checkStatus(final K key) {
		TaskFuture<ResultHolder> future = getFuture(key);
		if (future == null) {
			return;
		}
		if (!future.isDone()) {
			return;
		}
		ResultHolder<V> holder;
		try {
			holder = future.get();
		} catch (InterruptedException | ExecutionException e) {
			holder = new ResultHolder(e);
		}
		clearFuture(key);
		setResultHolder(key, holder);
		handleResult(holder);
	}

	/**
	 * Handle the result from the polling action.
	 *
	 * @param resultHolder the task action result
	 */
	protected void handleResult(final ResultHolder<V> resultHolder) {
		if (resultHolder.hasException()) {
			Exception excp = resultHolder.getException();
			handleExceptionResult(excp);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
		} else {
			// Successful Result
			V result = resultHolder.getResult();
			handleSuccessfulResult(result);
		}
	}

	/**
	 * Handle an exception occurred.
	 *
	 * @param excp the exception that occurred
	 */
	protected void handleExceptionResult(final Exception excp) {
		// Dispatch ERROR
	}

	/**
	 * Handle the result. Default behaviour is to set the result as the bean for the content.
	 *
	 * @param result the polling action result
	 */
	protected void handleSuccessfulResult(final V result) {
		// DISPATCH OK
	}

	/**
	 *
	 * @return the async action.
	 */
	protected ServiceAction<K, V> getServiceAction() {
		return serviceAction;
	}

	/**
	 * Handle the polling action.
	 *
	 * @param key the key for the task action
	 */
	protected void handleAsyncPollingAction(final K key) {

		clearFuture(key);
		clearResult(key);

		final ServiceAction<K, V> action = getServiceAction();
		if (action == null) {
			throw new IllegalStateException("No task service action has been defined. ");
		}

		final ResultHolder<V> result = new ResultHolder();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					V resp = action.service(key);
					result.setResult(resp);
				} catch (Exception e) {
					ServiceException excp = new ServiceException("Error calling service." + e.getMessage(), e);
					result.setException(excp);
				}
			}
		};
		try {
			TaskFuture future = TASK_MANAGER.submit(task, result);
			// Save the future
			setFuture(key, future);
		} catch (Exception e) {
			throw new SystemException("Could not start thread to process task action. " + e.getMessage());
		}
	}

	/**
	 * @return the polling action future object
	 * @param key the key for the task action
	 */
	protected TaskFuture<ResultHolder> getFuture(final K key) {
		return getTaskCache().get(key);
	}

	/**
	 * @param key the key value
	 * @param future the future holding the result
	 */
	protected void setFuture(final K key, final TaskFuture<ResultHolder> future) {
		getTaskCache().put(key, future);
	}

	/**
	 * Cancel and clear the future if there is one already running.
	 *
	 * @param key the key for the task action
	 */
	protected void clearFuture(final K key) {
		TaskFuture future = getFuture(key);
		if (future != null) {
			if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
			getTaskCache().remove(key);
		}
	}

	/**
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<Object, TaskFuture> getTaskCache() {
		String name = getTaskCacheName();
		Cache<Object, TaskFuture> cache = Caching.getCache(name, Object.class, TaskFuture.class);
		if (cache == null) {
			final CacheManager mgr = Caching.getCachingProvider().getCacheManager();
			MutableConfiguration<Object, TaskFuture> config = new MutableConfiguration<>();
			config.setTypes(Object.class, TaskFuture.class);
			config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(getTaskCacheDuration()));
			cache = mgr.createCache(name, config);
		}
		return cache;
	}

	protected String getTaskCacheName() {
		return getStoreKey().toString() + "-task";
	}

	protected Duration getTaskCacheDuration() {
		return new Duration(TimeUnit.MINUTES, 5);
	}

	/**
	 * @return the polling action future object
	 * @param key the key for the task action
	 */
	protected ResultHolder getResultHolder(final K key) {
		return getResultCache().get(key);
	}

	/**
	 * @param key the key value
	 * @param result the result holder
	 */
	protected void setResultHolder(final K key, final ResultHolder result) {
		getResultCache().put(key, result);
	}

	/**
	 * Clear the result cache.
	 *
	 * @param key the key for the result holder
	 */
	protected void clearResult(final K key) {
		getResultCache().remove(key);
	}

	/**
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<Object, ResultHolder> getResultCache() {
		String name = getTaskCacheName();
		Cache<Object, ResultHolder> cache = Caching.getCache(name, Object.class, ResultHolder.class);
		if (cache == null) {
			final CacheManager mgr = Caching.getCachingProvider().getCacheManager();
			MutableConfiguration<Object, ResultHolder> config = new MutableConfiguration<>();
			config.setTypes(Object.class, ResultHolder.class);
			config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(getTaskCacheDuration()));
			cache = mgr.createCache(name, config);
		}
		return cache;
	}

	protected String getResultCacheName() {
		return getStoreKey().toString() + "-result";
	}

	protected Duration getResultCacheDuration() {
		return new Duration(TimeUnit.MINUTES, 30);
	}

}
