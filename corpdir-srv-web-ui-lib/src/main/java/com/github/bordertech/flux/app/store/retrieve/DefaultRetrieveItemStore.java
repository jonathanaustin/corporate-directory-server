package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveItemApi;
import com.github.bordertech.flux.app.event.base.RetrieveActionType;
import com.github.bordertech.flux.app.event.RetrieveEvent;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.ModifyBaseEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.Objects;

/**
 * Default retrieve store.
 *
 * @param <K> the key type
 * @param <T> the item type
 * @param <R> the retrieve api type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultRetrieveItemStore<K, T, R extends RetrieveItemApi<K, T>> extends AbstractRetrieveStore implements RetrieveItemStore<K, T> {

	private final R api;

	public DefaultRetrieveItemStore(final DataApiType apiType, final StoreType storeType) {
		this(apiType, storeType, null);
	}

	public DefaultRetrieveItemStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
		api = (R) DataApiFactory.getInstance(apiType);
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

	@Override
	public ServiceStatus getRetrieveStatus(final K criteria) {
		return getResultStatus(RetrieveBaseEventType.RETRIEVE, criteria);
	}

	@Override
	public boolean isRetrieveDone(final K criteria) {
		return isResultDone(RetrieveBaseEventType.RETRIEVE, criteria);
	}

	@Override
	public T retrieve(final K criteria) throws ServiceException {
		// Retrieve and RetrieveAsync get treated the same
		return (T) getResultValue(RetrieveBaseEventType.RETRIEVE, criteria);
	}

	protected void handleRetrieveBaseEvents(final RetrieveEvent event) {
		RetrieveBaseEventType type = (RetrieveBaseEventType) event.getEventKey().getEventType();
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

	protected void handleModifyBaseEvents(final Event event) {
		ModifyBaseEventType type = (ModifyBaseEventType) event.getEventKey().getEventType();
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

	/**
	 *
	 * @param type the event type
	 * @return the wrapped API action for the event type.
	 */
	@Override
	protected ServiceAction<?, ?> getWrappedApiServiceAction(final RetrieveEventType type) {
		if (Objects.equals(type, RetrieveBaseEventType.RETRIEVE)) {
			return new ServiceAction<K, T>() {
				@Override
				public T service(final K key) throws ServiceException {
					return getRetrieveApi().retrieve(key);
				}
			};
		}
		return null;
	}

	protected R getRetrieveApi() {
		return api;
	}

}
