package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveTreeApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.ModifyTreeBaseEventType;
import com.github.bordertech.flux.app.event.base.RetrieveTreeBaseEventType;
import com.github.bordertech.wcomponents.task.service.ResultHolder;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceException;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jonathan
 */
public class DefaultRetrieveTreeStore<S, T, R extends RetrieveTreeApi<S, T>> extends DefaultRetrieveListStore<S, T, R> implements RetrieveTreeStore<S, T> {

	public DefaultRetrieveTreeStore(final DataApiType apiType, final StoreType storeType) {
		super(apiType, storeType);
	}

	public DefaultRetrieveTreeStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(apiType, storeType, qualifier);
	}

	@Override
	public void registerListeners() {
		// Retrieve Listeners
		for (RetrieveTreeBaseEventType type : RetrieveTreeBaseEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleRetrieveTreeBaseEvents(event);
				}
			};
			registerListener(type, listener);
		}

		// Action Listeners
		for (ModifyTreeBaseEventType type : ModifyTreeBaseEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleModifyTreeBaseEvents(event);
				}
			};
			registerListener(type, listener);
		}
	}

	@Override
	public boolean hasChildren(final T item) {
		return getRetrieveApi().hasChildren(item);
	}

	@Override
	public List<T> getChildren(final T item) throws ServiceException {
		return getResultValue(RetrieveTreeBaseEventType.RETRIEVE_CHILDREN, item);
	}

	@Override
	public List<T> getRootItems() throws ServiceException {
		return getResultValue(RetrieveTreeBaseEventType.RETRIEVE_ROOT, null);
	}

	@Override
	public String getItemLabel(final T item) {
		return getRetrieveApi().getItemLabel(item);
	}

	@Override
	public String getItemId(final T item) {
		return getRetrieveApi().getItemId(item);
	}

	@Override
	public ServiceStatus getRetrieveChildrenStatus(final T item) {
		return getResultStatus(RetrieveTreeBaseEventType.RETRIEVE_CHILDREN, item);
	}

	@Override
	public boolean isRetrieveChildrenDone(final T item) {
		return isResultDone(RetrieveTreeBaseEventType.RETRIEVE_CHILDREN, item);
	}

	@Override
	public ServiceStatus getRetrieveRootStatus() {
		return getResultStatus(RetrieveTreeBaseEventType.RETRIEVE_ROOT, null);
	}

	@Override
	public boolean isRetrieveRootDone() {
		return isResultDone(RetrieveTreeBaseEventType.RETRIEVE_ROOT, null);
	}

	protected void handleRetrieveTreeBaseEvents(final Event event) {
		RetrieveTreeBaseEventType type = (RetrieveTreeBaseEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case RETRIEVE_CHILDREN:
				handleRetrieveChildrenEvent((T) event.getData(), false);
				changed = true;
				break;
			case RETRIEVE_CHILDREN_ASYNC:
				handleRetrieveChildrenEvent((T) event.getData(), true);
				break;
			case RETRIEVE_CHILDREN_ASYNC_ERROR:
				handleRetrieveChildrenAsyncErrorEvent((ResultHolder<S, List<T>>) event.getData());
				changed = true;
				break;
			case RETRIEVE_CHILDREN_ASYNC_OK:
				handleRetrieveChildrenAsyncOKEvent((ResultHolder<S, List<T>>) event.getData());
				changed = true;
				break;

			case REFRESH_CHILDREN:
				handleRefreshChildrenEvent((T) event.getData(), false);
				changed = true;
				break;
			case REFRESH_CHILDREN_ASYNC:
				handleRefreshChildrenEvent((T) event.getData(), true);
				changed = true;
				break;

			case RETRIEVE_ROOT:
				handleRetrieveRootEvent(false);
				changed = true;
				break;
			case RETRIEVE_ROOT_ASYNC:
				handleRetrieveRootEvent(true);
				break;
			case RETRIEVE_ROOT_ASYNC_ERROR:
				handleRetrieveRootAsyncErrorEvent((ResultHolder<S, List<T>>) event.getData());
				changed = true;
				break;
			case RETRIEVE_ROOT_ASYNC_OK:
				handleRetrieveRootAsyncOKEvent((ResultHolder<S, List<T>>) event.getData());
				changed = true;
				break;

			case REFRESH_ROOT:
				handleRefreshRootEvent(false);
				changed = true;
				break;
			case REFRESH_ROOT_ASYNC:
				handleRefreshRootEvent(true);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}

	}

	protected void handleRetrieveChildrenEvent(final T item, final boolean async) {
		handleServiceCall(RetrieveTreeBaseEventType.RETRIEVE_CHILDREN, item, async);
	}

	protected void handleRetrieveChildrenAsyncOKEvent(final ResultHolder<S, List<T>> holder) {
		// OK
	}

	protected void handleRetrieveChildrenAsyncErrorEvent(final ResultHolder<S, List<T>> holder) {
		// ERROR
	}

	protected void handleRefreshChildrenEvent(final T item, final boolean async) {
		clearResultHolder(RetrieveTreeBaseEventType.RETRIEVE_CHILDREN, item);
		handleRetrieveChildrenEvent(item, async);
	}

	protected void handleRetrieveRootEvent(final boolean async) {
		handleServiceCall(RetrieveTreeBaseEventType.RETRIEVE_ROOT, null, async);
	}

	protected void handleRetrieveRootAsyncOKEvent(final ResultHolder<S, List<T>> holder) {
		// OK
	}

	protected void handleRetrieveRootAsyncErrorEvent(final ResultHolder<S, List<T>> holder) {
		// ERROR
	}

	protected void handleRefreshRootEvent(final boolean async) {
		clearResultHolder(RetrieveTreeBaseEventType.RETRIEVE_ROOT, null);
		handleRetrieveRootEvent(async);
	}

	protected void handleModifyTreeBaseEvents(final Event event) {
		ModifyTreeBaseEventType type = (ModifyTreeBaseEventType) event.getEventKey().getEventType();
		boolean changed = false;
		switch (type) {
			case ADD_CHILD:
				handleAddChildEvent(event);
				changed = true;
				break;
			case REMOVE_CHILD:
				handleRemoveChildEvent(event);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeEvent(type);
		}
	}

	protected void handleAddChildEvent(final Event event) {
		// Create Action
	}

	protected void handleRemoveChildEvent(final Event event) {
		// Update action
	}

	@Override
	protected RetrieveEventType getAsyncOutcomeEvent(final RetrieveEventType type, final boolean excp) {
		if (Objects.equals(type, RetrieveTreeBaseEventType.RETRIEVE_CHILDREN)) {
			return excp ? RetrieveTreeBaseEventType.RETRIEVE_CHILDREN_ASYNC_ERROR : RetrieveTreeBaseEventType.RETRIEVE_CHILDREN_ASYNC_OK;
		}
		if (Objects.equals(type, RetrieveTreeBaseEventType.RETRIEVE_ROOT)) {
			return excp ? RetrieveTreeBaseEventType.RETRIEVE_ROOT_ASYNC_ERROR : RetrieveTreeBaseEventType.RETRIEVE_ROOT_ASYNC_OK;
		}
		return super.getAsyncOutcomeEvent(type, excp);
	}

	@Override
	protected ServiceAction<?, ?> getWrappedApiServiceAction(final RetrieveEventType type) {
		if (Objects.equals(type, RetrieveTreeBaseEventType.RETRIEVE_CHILDREN)) {
			return new ServiceAction<T, List<T>>() {
				@Override
				public List<T> service(final T item) throws ServiceException {
					return getRetrieveApi().getChildren(item);
				}
			};
		}
		if (Objects.equals(type, RetrieveTreeBaseEventType.RETRIEVE_ROOT)) {
			return new ServiceAction<T, List<T>>() {
				@Override
				public List<T> service(final T item) throws ServiceException {
					return getRetrieveApi().getRootItems();
				}
			};
		}
		return super.getWrappedApiServiceAction(type);
	}

}
