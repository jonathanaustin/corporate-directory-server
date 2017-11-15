package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dataapi.retrieve.RetrieveApi;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.event.base.ModifyEventType;
import com.github.bordertech.flux.event.base.RetrieveEventType;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.flux.store.StoreException;
import com.github.bordertech.wcomponents.task.service.ResultHolder;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceException;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;
import com.github.bordertech.wcomponents.task.service.ServiceUtil;

/**
 * Default retrieve store.
 *
 * @param <S> the criteria type
 * @param <T> the response type
 * @param <R> the retrieve api type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultRetrieveStore<S, T, R extends RetrieveApi<S, T>> extends DefaultStore implements RetrieveStore<S, T> {

	private final R api;

	public DefaultRetrieveStore(final DataApiType apiType, final StoreType storeType) {
		this(apiType, storeType, null);
	}

	public DefaultRetrieveStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
		api = (R) DataApiFactory.getInstance(apiType);
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
		for (ModifyEventType type : ModifyEventType.values()) {
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
	public ServiceStatus getRetrieveStatus(final S criteria) {
		return getResultStatus(RetrieveEventType.RETRIEVE, criteria);
	}

	@Override
	public boolean isRetrieveDone(final S criteria) {
		ServiceStatus status = getRetrieveStatus(criteria);
		return status == ServiceStatus.COMPLETE || status == ServiceStatus.ERROR;
	}

	@Override
	public T getRetrieveValue(final S criteria) throws StoreException {
		// Retrieve and RetrieveAsync get treated the same
		return getResultValue(RetrieveEventType.RETRIEVE, criteria);
	}

	protected void handleRetrieveEvents(final Event event) {
		RetrieveEventType type = (RetrieveEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case RETRIEVE:
				handleRetrieveEvent((S) event.getData(), false);
				changed = true;
				break;
			case RETRIEVE_ASYNC:
				handleRetrieveEvent((S) event.getData(), true);
				break;
			case RETRIEVE_ASYNC_ERROR:
				handleRetrieveAsyncErrorEvent((ResultHolder<S, T>) event.getData());
				changed = true;
				break;
			case RETRIEVE_ASYNC_OK:
				handleRetrieveAsyncOKEvent((ResultHolder<S, T>) event.getData());
				changed = true;
				break;

			case REFRESH:
				handleRefreshEvent((S) event.getData(), false);
				changed = true;
				break;
			case REFRESH_ASYNC:
				handleRefreshEvent((S) event.getData(), true);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}

	}

	protected void handleRetrieveEvent(final S criteria, final boolean async) {
		handleServiceCall(RetrieveEventType.RETRIEVE, criteria, async);
	}

	protected void handleRetrieveAsyncOKEvent(final ResultHolder<S, T> holder) {
		// OK
	}

	protected void handleRetrieveAsyncErrorEvent(final ResultHolder<S, T> holder) {
		// ERROR
	}

	protected void handleRefreshEvent(final S criteria, final boolean async) {
		clearResultHolder(RetrieveEventType.RETRIEVE, criteria);
		handleRetrieveEvent(criteria, async);
	}

	protected void handleActionEvents(final Event event) {
		ModifyEventType type = (ModifyEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case CREATE:
				handleCreateEvent(event);
				changed = true;
				break;
			case UPDATE:
				handleUpdateEvent(event);
				changed = true;
				break;
			case DELETE:
				handleDeleteEvent(event);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}
	}

	protected void handleCreateEvent(final Event event) {
		// Create Action
	}

	protected void handleUpdateEvent(final Event event) {
		// Update action
	}

	protected void handleDeleteEvent(final Event event) {
		// Delete action
	}

	protected String getResultCacheKey(final RetrieveEventType type, final Object criteria) {
		String typeDesc = type.toString();
		String suffix = criteria == null ? "" : criteria.toString();
		return getStoreKey().toString() + "-" + typeDesc + "-" + suffix;
	}

	protected ResultHolder<S, T> getResultHolder(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getResultHolder(key);
	}

	protected void setResultHolder(final RetrieveEventType type, final ResultHolder<S, T> resultHolder) {
		String key = getResultCacheKey(type, resultHolder.getCriteria());
		ServiceUtil.setResultHolder(key, resultHolder);
	}

	protected void clearResultHolder(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		ServiceUtil.clearResult(key);
	}

	protected T getResultValue(final RetrieveEventType type, final Object criteria) throws StoreException {

		// Check if have result
		ResultHolder<S, T> holder = getResultHolder(type, criteria);
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

	public ServiceStatus getResultStatus(final RetrieveEventType type, final Object criteria) {
		checkAsyncResult(type, criteria);
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getServiceStatus(key);
	}

	protected void checkAsyncResult(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		// Check if async result available
		ResultHolder resultHolder = ServiceUtil.checkASyncResult(key);
		if (resultHolder != null) {
			RetrieveEventType event = getAsyncOutcomeEvent(type, resultHolder.hasException());
			if (event != null) {
				dispatchResultEvent(event, resultHolder);
			}
		}
	}

	protected RetrieveEventType getAsyncOutcomeEvent(final RetrieveEventType type, final boolean excp) {
		switch (type) {
			case RETRIEVE:
				return excp ? RetrieveEventType.RETRIEVE_ASYNC_ERROR : RetrieveEventType.RETRIEVE_ASYNC_OK;
			default:
				return null;
		}
	}

	protected void handleServiceCall(final RetrieveEventType type, final Object criteria, final boolean async) {
		String key = getResultCacheKey(type, criteria);
		ServiceAction action = getWrappedApiServiceAction(type);
		if (async) {
			ServiceUtil.handleAsyncServiceCall(key, criteria, action);
		} else {
			ServiceUtil.handleServiceCall(key, criteria, action);
		}
	}

	/**
	 *
	 * @param type the event type
	 * @return the wrapped API action for the event type.
	 */
	protected ServiceAction<S, T> getWrappedApiServiceAction(final RetrieveEventType type) {
		switch (type) {
			case RETRIEVE:
				return new ServiceAction<S, T>() {
					@Override
					public T service(final S criteria) throws ServiceException {
						return getRetrieveApi().retrieve(criteria);
					}
				};
			default:
				return null;
		}
	}

	protected R getRetrieveApi() {
		return api;
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

}
