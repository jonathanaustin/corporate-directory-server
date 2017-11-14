package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.wcomponents.task.TaskFuture;
import com.github.bordertech.wcomponents.task.TaskManager;
import com.github.bordertech.wcomponents.task.TaskManagerFactory;
import com.github.bordertech.wcomponents.task.service.ResultHolder;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceException;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Default retrieve async store.
 *
 * @param <S> the criteria type
 * @param <T> the response type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultRetrieveAsyncStore<S, T> extends DefaultRetrieveStore<S, T> implements RetrieveAsyncStore<S, T> {

	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

	public DefaultRetrieveAsyncStore(final DataApiType apiType, final StoreType storeType) {
		super(apiType, storeType);
	}

	public DefaultRetrieveAsyncStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(apiType, storeType, qualifier);
	}

	public DefaultRetrieveAsyncStore(final ServiceAction<S, T> serviceAction, final StoreType storeType) {
		super(serviceAction, storeType);
	}

	public DefaultRetrieveAsyncStore(final ServiceAction<S, T> serviceAction, final StoreType storeType, final String qualifier) {
		super(serviceAction, storeType, qualifier);
	}

	@Override
	public ServiceStatus getStatus(final S criteria) {
		checkStatus(criteria);

		// Check for result
		ResultHolder<S, T> holder = getResultHolder(criteria);
		if (holder != null) {
			return holder.hasException() ? ServiceStatus.ERROR : ServiceStatus.COMPLETE;
		}

		// Check if processing
		TaskFuture<ResultHolder> future = getFuture(criteria);
		return future == null ? ServiceStatus.NOT_STARTED : ServiceStatus.PROCESSING;
	}

	@Override
	public boolean isDone(final S criteria) {
		ServiceStatus status = getStatus(criteria);
		return status == ServiceStatus.COMPLETE || status == ServiceStatus.ERROR;
	}

	@Override
	protected void handleRetrieveOK(final ResultHolder<S, T> holder) {
		super.handleRetrieveOK(holder);
		clearFuture(holder.getCriteria());
	}

	@Override
	protected void handleRetrieveError(final ResultHolder<S, T> holder) {
		super.handleRetrieveError(holder);
		clearFuture(holder.getCriteria());
	}

	@Override
	protected void handleRefresh(final S criteria) {
		clearFuture(criteria);
		super.handleRefresh(criteria);
	}

	protected synchronized void checkStatus(final S criteria) {
		TaskFuture<ResultHolder> future = getFuture(criteria);
		if (future == null) {
			return;
		}
		if (!future.isDone()) {
			return;
		}
		if (future.isCancelled()) {
			clearFuture(criteria);
			return;
		}
		ResultHolder<S, T> holder;
		try {
			holder = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new SystemException("Error processing the service. " + e.getMessage(), e);
		}
		// Make sure the results from the future are only processed once
		// There is a small window between the result getting from the future into the cache via the dispatcher
		if (!holder.isProcessed()) {
			holder.setProcessed(true);
			handleResult(holder);
		}
	}

	@Override
	protected ResultHolder<S, T> handleServiceCall(final S criteria) {

		// Check if result already in cache
		final ResultHolder<S, T> cache = getResultHolder(criteria);
		if (cache != null) {
			return cache;
		}

		// Check if already processing
		if (getFuture(criteria) != null) {
			return null;
		}

		// Setup service call
		final ServiceAction<S, T> action = getServiceAction();
		if (action == null) {
			throw new IllegalStateException("No task service action has been defined. ");
		}

		final ResultHolder<S, T> result = new ResultHolder(criteria);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					T resp = action.service(criteria);
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
			setFuture(criteria, future);
		} catch (Exception e) {
			throw new SystemException("Could not start thread to process task action. " + e.getMessage());
		}
		return null;
	}

	/**
	 * @return the future holding the service action
	 * @param criteria the key for the task action
	 */
	protected TaskFuture<ResultHolder> getFuture(final S criteria) {
		return getFutureCache().get(criteria);
	}

	/**
	 * @param criteria the criteria value
	 * @param future the future holding the result
	 */
	protected void setFuture(final S criteria, final TaskFuture<ResultHolder> future) {
		getFutureCache().put(criteria, future);
	}

	/**
	 * Cancel and clear the future if there is one already running.
	 *
	 * @param criteria the key for the task action
	 */
	protected void clearFuture(final S criteria) {
		TaskFuture future = getFuture(criteria);
		if (future != null) {
			if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
			getFutureCache().remove(criteria);
		}
	}

	/**
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<Object, TaskFuture> getFutureCache() {
		String name = getFutureCacheName();
		Cache<Object, TaskFuture> cache = Caching.getCache(name, Object.class, TaskFuture.class);
		if (cache == null) {
			final CacheManager mgr = Caching.getCachingProvider().getCacheManager();
			MutableConfiguration<Object, TaskFuture> config = new MutableConfiguration<>();
			config.setTypes(Object.class, TaskFuture.class);
			config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(getFutureCacheDuration()));
			cache = mgr.createCache(name, config);
		}
		return cache;
	}

	protected String getFutureCacheName() {
		return getStoreKey().toString() + "-task";
	}

	protected Duration getFutureCacheDuration() {
		return new Duration(TimeUnit.MINUTES, 5);
	}

}
