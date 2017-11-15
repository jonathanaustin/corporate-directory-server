package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.ModifyBaseEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.Objects;

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
public class DefaultRetrieveStore<S, T, R extends RetrieveApi<S, T>> extends AbstractRetrieveStore<S, T> implements RetrieveStore<S, T> {

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
		for (RetrieveBaseEventType type : RetrieveBaseEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleRetrieveBaseEvents(event);
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
	public ServiceStatus getRetrieveStatus(final S criteria) {
		return getResultStatus(RetrieveBaseEventType.RETRIEVE, criteria);
	}

	@Override
	public boolean isRetrieveDone(final S criteria) {
		return isResultDone(RetrieveBaseEventType.RETRIEVE, criteria);
	}

	@Override
	public T retrieve(final S criteria) throws ServiceException {
		// Retrieve and RetrieveAsync get treated the same
		return getResultValue(RetrieveBaseEventType.RETRIEVE, criteria);
	}

	protected void handleRetrieveBaseEvents(final Event event) {
		RetrieveBaseEventType type = (RetrieveBaseEventType) event.getEventKey().getEventType();
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
		handleServiceCall(RetrieveBaseEventType.RETRIEVE, criteria, async);
	}

	protected void handleRetrieveAsyncOKEvent(final ResultHolder<S, T> holder) {
		// OK
	}

	protected void handleRetrieveAsyncErrorEvent(final ResultHolder<S, T> holder) {
		// ERROR
	}

	protected void handleRefreshEvent(final S criteria, final boolean async) {
		clearResultHolder(RetrieveBaseEventType.RETRIEVE, criteria);
		handleRetrieveEvent(criteria, async);
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

	@Override
	protected RetrieveEventType getAsyncOutcomeEvent(final RetrieveEventType type, final boolean excp) {
		if (Objects.equals(type, RetrieveBaseEventType.RETRIEVE)) {
			return excp ? RetrieveBaseEventType.RETRIEVE_ASYNC_ERROR : RetrieveBaseEventType.RETRIEVE_ASYNC_OK;
		}
		return null;
	}

	/**
	 *
	 * @param type the event type
	 * @return the wrapped API action for the event type.
	 */
	@Override
	protected ServiceAction<?, ?> getWrappedApiServiceAction(final RetrieveEventType type) {
		if (Objects.equals(type, RetrieveBaseEventType.RETRIEVE)) {
			return new ServiceAction<S, T>() {
				@Override
				public T service(final S criteria) throws ServiceException {
					return getRetrieveApi().retrieve(criteria);
				}
			};
		}
		return null;
	}

	protected R getRetrieveApi() {
		return api;
	}

}
