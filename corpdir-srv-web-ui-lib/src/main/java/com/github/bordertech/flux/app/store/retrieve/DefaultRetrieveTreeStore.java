package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveTreeApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.ModifyTreeBaseEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jonathan
 */
public class DefaultRetrieveTreeStore<K, T, S, R extends RetrieveTreeApi<K, T, S>> extends DefaultRetrieveEntityStore<K, T, S, R> implements RetrieveTreeStore<K, T, S> {

	public DefaultRetrieveTreeStore(final DataApiType apiType, final StoreType storeType) {
		super(apiType, storeType);
	}

	public DefaultRetrieveTreeStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(apiType, storeType, qualifier);
	}

	@Override
	public void registerListeners() {

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
		return (List<T>) getResultValue(RetrieveBaseEventType.CHILDREN, item);
	}

	@Override
	public List<T> getRootItems() throws ServiceException {
		return (List<T>) getResultValue(RetrieveBaseEventType.ROOT, null);
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
	public ServiceStatus getChildrenStatus(final T item) {
		return getResultStatus(RetrieveBaseEventType.CHILDREN, item);
	}

	@Override
	public boolean isChildrenDone(final T item) {
		return isResultDone(RetrieveBaseEventType.CHILDREN, item);
	}

	@Override
	public ServiceStatus getRootItemsStatus() {
		return getResultStatus(RetrieveBaseEventType.ROOT, null);
	}

	@Override
	public boolean isRootItemsDone() {
		return isResultDone(RetrieveBaseEventType.ROOT, null);
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
	protected ServiceAction<?, ?> getWrappedApiServiceAction(final RetrieveEventType type) {
		if (Objects.equals(type, RetrieveBaseEventType.CHILDREN)) {
			return new ServiceAction<T, List<T>>() {
				@Override
				public List<T> service(final T item) throws ServiceException {
					return getRetrieveApi().getChildren(item);
				}
			};
		}
		if (Objects.equals(type, RetrieveBaseEventType.ROOT)) {
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
