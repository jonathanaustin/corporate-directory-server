package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.event.base.ActionEventType;
import com.github.bordertech.flux.event.base.RetrieveEventType;
import com.github.bordertech.wcomponents.task.service.ResultHolder;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceException;
import java.util.concurrent.TimeUnit;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Default retrieve store.
 *
 * @param <S> the criteria type
 * @param <T> the response type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultRetrieveStore<S, T> extends DefaultStore implements RetrieveStore<S, T> {

	private final ServiceAction<S, T> serviceAction;

	public DefaultRetrieveStore(final ServiceAction<S, T> serviceAction, final StoreType storeType) {
		this(serviceAction, storeType, null);
	}

	public DefaultRetrieveStore(final ServiceAction<S, T> serviceAction, final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
		this.serviceAction = serviceAction;
	}

	@Override
	public void registerListeners() {
		// Retrieve Listeners
		for (RetrieveEventType type : RetrieveEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleRetrieveEvents(event);
				}
			};
			registerListener(type, listener);
		}

		// Action Listeners
		for (ActionEventType type : ActionEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleActionEvents(event);
				}
			};
			registerListener(type, listener);
		}

	}

	@Override
	public T getValue(final S criteria) throws StoreException {

		// Check if have result
		ResultHolder<S, T> holder = getResultHolder(criteria);
		if (holder == null) {
			throw new StoreException("No value for this criteria.");
		}

		if (holder.hasException()) {
			Exception excp = holder.getException();
			if (excp instanceof StoreException) {
				throw (StoreException) excp;
			}
			throw new StoreException(excp.getMessage(), excp);
		}

		return holder.getResult();
	}

	protected void handleRetrieveEvents(final Event event) {
		RetrieveEventType type = (RetrieveEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case RETRIEVE:
				handleRetrieve((S) event.getData());
				break;
			case RETRIEVE_ERROR:
				handleRetrieveError((ResultHolder<S, T>) event.getData());
				changed = true;
				break;
			case RETRIEVE_OK:
				handleRetrieveOK((ResultHolder<S, T>) event.getData());
				changed = true;
				break;

			case REFRESH:
				handleRefresh((S) event.getData());
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}

	}

	protected void handleRetrieve(final S criteria) {
		ResultHolder result = handleServiceCall(criteria);
		handleResult(result);
	}

	protected void handleRetrieveOK(final ResultHolder<S, T> holder) {
		setResultHolder(holder);
	}

	protected void handleRetrieveError(final ResultHolder<S, T> holder) {
		setResultHolder(holder);
	}

	protected void handleRefresh(final S criteria) {
		clearResult(criteria);
		handleRetrieve(criteria);
	}

	protected void handleActionEvents(final Event event) {
		ActionEventType type = (ActionEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case CREATE:
				handleCreate((ResultHolder<S, T>) event.getData());
				changed = true;
				break;
			case UPDATE:
				handleUpdate((ResultHolder<S, T>) event.getData());
				changed = true;
				break;
			case DELETE:
				handleDelete((ResultHolder<S, T>) event.getData());
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}
	}

	protected void handleCreate(final ResultHolder<S, T> holder) {
		setResultHolder(holder);
	}

	protected void handleUpdate(final ResultHolder<S, T> holder) {
		setResultHolder(holder);
	}

	protected void handleDelete(final ResultHolder<S, T> holder) {
		clearResult(holder.getCriteria());
	}

	protected ResultHolder<S, T> handleServiceCall(final S criteria) {

		// Check Cache
		ResultHolder<S, T> result = getResultHolder(criteria);
		if (result != null) {
			return result;
		}

		// Do service call
		final ServiceAction<S, T> action = getServiceAction();
		if (action == null) {
			throw new IllegalStateException("No task service action has been defined. ");
		}

		result = new ResultHolder(criteria);
		try {
			T resp = action.service(criteria);
			result.setResult(resp);
		} catch (Exception e) {
			ServiceException excp = new ServiceException("Error calling service." + e.getMessage(), e);
			result.setException(excp);
		}
		return result;
	}

	/**
	 * Handle the result from the service action.
	 *
	 * @param resultHolder the service action result
	 */
	protected void handleResult(final ResultHolder<S, T> resultHolder) {
		if (resultHolder == null) {
			return;
		}
		if (resultHolder.hasException()) {
			dispatchResultEvent(RetrieveEventType.RETRIEVE_ERROR, resultHolder);
		} else {
			dispatchResultEvent(RetrieveEventType.RETRIEVE_OK, resultHolder);
		}
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param result the event data
	 */
	protected void dispatchResultEvent(final RetrieveEventType eventType, final ResultHolder<S, T> result) {
		String qualifier = getStoreKey().getQualifier();
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, qualifier), result);
		getDispatcher().dispatch(event);
	}

	/**
	 *
	 * @return the async action.
	 */
	protected ServiceAction<S, T> getServiceAction() {
		return serviceAction;
	}

	/**
	 * @return the polling action future object
	 * @param criteria the key for the task action
	 */
	protected ResultHolder<S, T> getResultHolder(final S criteria) {
		return getResultCache().get(criteria);
	}

	/**
	 * @param result the result holder
	 */
	protected void setResultHolder(final ResultHolder<S, T> result) {
		getResultCache().put(result.getCriteria(), result);
	}

	/**
	 * Clear the result cache.
	 *
	 * @param criteria the key for the result holder
	 */
	protected void clearResult(final S criteria) {
		getResultCache().remove(criteria);
	}

	/**
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<Object, ResultHolder> getResultCache() {
		String name = getResultCacheName();
		Cache<Object, ResultHolder> cache = Caching.getCache(name, Object.class, ResultHolder.class);
		if (cache == null) {
			final CacheManager mgr = Caching.getCachingProvider().getCacheManager();
			MutableConfiguration<Object, ResultHolder> config = new MutableConfiguration<>();
			config.setTypes(Object.class, ResultHolder.class);
			config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(getResultCacheDuration()));
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
