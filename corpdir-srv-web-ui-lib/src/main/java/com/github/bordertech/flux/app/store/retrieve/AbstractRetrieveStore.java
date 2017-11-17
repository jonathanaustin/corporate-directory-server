package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.app.event.RetrieveActionType;
import com.github.bordertech.flux.app.event.RetrieveEvent;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.ModifyBaseEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.key.StoreKey;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import com.github.bordertech.taskmanager.service.ServiceUtil;

/**
 * Abstract retrieve store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public abstract class AbstractRetrieveStore extends DefaultStore implements RetrieveStore {

	public AbstractRetrieveStore(final StoreKey storeKey) {
		super(storeKey);
	}

	@Override
	public ServiceStatus getEventStatus(final RetrieveEventType type, final Object criteria) {
		checkAsyncResult(type, criteria);
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getServiceStatus(key);
	}

	@Override
	public boolean isEventDone(final RetrieveEventType type, final Object criteria) {
		ServiceStatus status = getEventStatus(type, criteria);
		return status == ServiceStatus.COMPLETE || status == ServiceStatus.ERROR;
	}

	@Override
	public Object getEventResult(final RetrieveEventType type, final Object criteria) {

		// Check if have result
		ResultHolder<?, ?> holder = getResultHolder(type, criteria);
		if (holder == null) {
			if (getEventStatus(type, criteria) == ServiceStatus.PROCESSING) {
				throw new IllegalStateException("Item is still being retrieved.");
			}
			// Call SYNC (ie retrieve immediately)
			holder = handleCallAction(type, criteria, false);
		}

		if (holder.isException()) {
			Exception excp = holder.getException();
			if (excp instanceof ServiceException) {
				throw (ServiceException) excp;
			}
			throw new ServiceException(excp.getMessage(), excp);
		}

		return holder.getResult();
	}

	@Override
	public void registerListeners() {
		// Retrieve Listeners
		for (RetrieveBaseEventType type : RetrieveBaseEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleRetrieveBaseEvents((RetrieveEvent) event);
				}
			};
			registerListener(type, listener);
		}
		// Action Listeners
		for (ModifyBaseEventType type : ModifyBaseEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleModifyBaseEvents(event);
				}
			};
			registerListener(type, listener);
		}
	}

	protected void handleModifyBaseEvents(final Event event) {
		ModifyBaseEventType type = (ModifyBaseEventType) event.getKey().getType();
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

	protected void handleRetrieveBaseEvents(final RetrieveEvent event) {
		RetrieveBaseEventType type = (RetrieveBaseEventType) event.getKey().getType();
		RetrieveActionType action = event.getActionType();
		boolean changed = false;
		switch (action) {
			case CALL_SYNC:
				handleCallAction(type, event.getData(), false);
				changed = true;
				break;
			case CALL_ASYNC:
				handleCallAction(type, event.getData(), true);
				break;
			case ASYNC_ERROR:
				handleAsyncErrorAction(type, (ResultHolder) event.getData());
				changed = true;
				break;
			case ASYNC_OK:
				handleAsyncOKAction(type, (ResultHolder) event.getData());
				changed = true;
				break;

			case REFRESH_SYNC:
				handleRefreshAction(type, event.getData(), false);
				changed = true;
				break;
			case REFRESH_ASYNC:
				handleRefreshAction(type, event.getData(), true);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}
	}

	protected ResultHolder<?, ?> handleCallAction(final RetrieveEventType type, final Object criteria, final boolean async) {
		String key = getResultCacheKey(type, criteria);
		ServiceAction action = new ServiceAction() {
			@Override
			public Object service(final Object criteria) {
				return doRetrieveServiceCall(type, criteria);
			}
		};
		if (async) {
			ServiceUtil.handleAsyncServiceCall(key, criteria, action);
			return null;
		} else {
			return ServiceUtil.handleServiceCall(key, criteria, action);
		}
	}

	protected void handleAsyncOKAction(final RetrieveEventType type, final ResultHolder<?, ?> holder) {
		// OK
	}

	protected void handleAsyncErrorAction(final RetrieveEventType type, final ResultHolder<?, ?> holder) {
		// ERROR
	}

	protected void handleRefreshAction(final RetrieveEventType type, final Object criteria, final boolean async) {
		clearResultHolder(type, criteria);
		handleCallAction(type, criteria, async);
	}

	protected String getResultCacheKey(final RetrieveEventType type, final Object criteria) {
		String typeDesc = type.toString();
		String suffix = criteria == null ? "" : criteria.toString();
		return getKey().toString() + "-" + typeDesc + "-" + suffix;
	}

	protected ResultHolder<?, ?> getResultHolder(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getResultHolder(key);
	}

	protected void setResultHolder(final RetrieveEventType type, final ResultHolder<?, ?> resultHolder) {
		String key = getResultCacheKey(type, resultHolder.getMetaData());
		ServiceUtil.setResultHolder(key, resultHolder);
	}

	protected void clearResultHolder(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		ServiceUtil.clearResult(key);
	}

	protected void checkAsyncResult(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		// Check if async result available
		ResultHolder resultHolder = ServiceUtil.checkASyncResult(key);
		if (resultHolder != null) {
			RetrieveActionType action = resultHolder.isException() ? RetrieveActionType.ASYNC_ERROR : RetrieveActionType.ASYNC_OK;
			dispatchResultEvent(type, action, resultHolder);
		}
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param action the retrieve action
	 * @param result the event data
	 */
	protected void dispatchResultEvent(final RetrieveEventType eventType, final RetrieveActionType action, final ResultHolder<?, ?> result) {
		String qualifier = getKey().getQualifier();
		DefaultEvent event = new RetrieveEvent(eventType, qualifier, result, action);
		getDispatcher().dispatch(event);
	}

	/**
	 *
	 * @param type the event type
	 * @param criteria the criteria
	 * @return the result
	 */
	protected abstract Object doRetrieveServiceCall(final RetrieveEventType type, final Object criteria);

}
