package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.wcomponents.task.service.RetrieveServiceUtil;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dataapi.retrieve.RetrieveApiServiceAction;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.event.base.ModifyEventType;
import com.github.bordertech.flux.event.base.RetrieveEventType;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.flux.store.StoreException;
import com.github.bordertech.wcomponents.task.service.ResultHolder;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;

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

	public DefaultRetrieveStore(final DataApiType apiType, final StoreType storeType) {
		this(apiType, storeType, null);
	}

	public DefaultRetrieveStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		this(new RetrieveApiServiceAction<S, T>(apiType), storeType, qualifier);
	}

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
	public ServiceStatus getStatus(final S criteria) {
		checkAsyncResult(criteria);
		return RetrieveServiceUtil.getServiceStatus(getCacheKey(criteria));
	}

	@Override
	public boolean isDone(final S criteria) {
		ServiceStatus status = getStatus(criteria);
		return status == ServiceStatus.COMPLETE || status == ServiceStatus.ERROR;
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
				changed = true;
				break;
			case RETRIEVE_ASYNC:
				handleRetrieveAsync((S) event.getData());
				break;
			case RETRIEVE_ASYNC_ERROR:
				handleRetrieveAsyncError((ResultHolder<S, T>) event.getData());
				changed = true;
				break;
			case RETRIEVE_ASYNC_OK:
				handleRetrieveAsyncOK((ResultHolder<S, T>) event.getData());
				changed = true;
				break;

			case REFRESH:
				handleRefresh((S) event.getData());
				changed = true;
				break;
			case REFRESH_ASYNC:
				handleRefreshAsync((S) event.getData());
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
		String key = getCacheKey(criteria);
		RetrieveServiceUtil.handleServiceCall(key, criteria, getServiceAction());
	}

	protected void handleRetrieveAsync(final S criteria) {
		String key = getCacheKey(criteria);
		RetrieveServiceUtil.handleAsyncServiceCall(key, criteria, getServiceAction());
	}

	protected void handleRetrieveAsyncOK(final ResultHolder<S, T> holder) {
		// OK
	}

	protected void handleRetrieveAsyncError(final ResultHolder<S, T> holder) {
		// ERROR
	}

	protected void handleRefresh(final S criteria) {
		String key = getCacheKey(criteria);
		RetrieveServiceUtil.clearResult(key);
		handleRetrieve(criteria);
	}

	protected void handleRefreshAsync(final S criteria) {
		String key = getCacheKey(criteria);
		RetrieveServiceUtil.clearResult(key);
		handleRetrieveAsync(criteria);
	}

	protected void handleActionEvents(final Event event) {
		ModifyEventType type = (ModifyEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case CREATE:
				handleCreate(event);
				changed = true;
				break;
			case UPDATE:
				handleUpdate(event);
				changed = true;
				break;
			case DELETE:
				handleDelete(event);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}
	}

	protected void handleCreate(final Event event) {
		// Create Action
	}

	protected void handleUpdate(final Event event) {
		// Update action
	}

	protected void handleDelete(final Event event) {
		// Delete action
	}

	protected void checkAsyncResult(final S criteria) {
		String key = getCacheKey(criteria);
		// Check if async result available
		ResultHolder resultHolder = RetrieveServiceUtil.checkASyncResult(key);
		if (resultHolder != null) {
			if (resultHolder.hasException()) {
				dispatchResultEvent(RetrieveEventType.RETRIEVE_ASYNC_ERROR, resultHolder);
			} else {
				dispatchResultEvent(RetrieveEventType.RETRIEVE_ASYNC_OK, resultHolder);
			}
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

	protected String getCacheKey(final S criteria) {
		return getStoreKey().toString() + "-" + criteria.toString();
	}

	protected ResultHolder<S, T> getResultHolder(final S criteria) {
		String key = getCacheKey(criteria);
		return RetrieveServiceUtil.getResultHolder(key);
	}

	protected void setResultHolder(final S criteria, final ResultHolder<S, T> resultHolder) {
		String key = getCacheKey(criteria);
		RetrieveServiceUtil.setResultHolder(key, resultHolder);
	}

}
