package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.CrudTreeApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.ModifyTreeBaseEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 * @param <S> the key type
 * @param <T> the item type
 * @param <K> the item key type
 * @param <D> the data API type
 *
 * @author jonathan
 */
public class DefaultRetrieveTreeStore<S, T, K, D extends CrudTreeApi<S, T, K>> extends DefaultRetrieveEntityStore<S, T, K, D> implements RetrieveTreeStore<S, T, K> {

	public DefaultRetrieveTreeStore(final D api, final StoreType storeType) {
		super(api, storeType);
	}

	public DefaultRetrieveTreeStore(final D api, final StoreType storeType, final String qualifier) {
		super(api, storeType, qualifier);
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
		return getDataApi().hasChildren(item);
	}

	@Override
	public List<T> getChildren(final T item) {
		return (List<T>) getEventResult(RetrieveBaseEventType.CHILDREN, item);
	}

	@Override
	public List<T> getRootItems() {
		return (List<T>) getEventResult(RetrieveBaseEventType.ROOT, null);
	}

	@Override
	public String getItemLabel(final T item) {
		return getDataApi().getItemLabel(item);
	}

	@Override
	public String getItemId(final T item) {
		return getDataApi().getItemId(item);
	}

	@Override
	public ServiceStatus getChildrenStatus(final T item) {
		return getEventStatus(RetrieveBaseEventType.CHILDREN, item);
	}

	@Override
	public boolean isChildrenDone(final T item) {
		return isEventDone(RetrieveBaseEventType.CHILDREN, item);
	}

	@Override
	public ServiceStatus getRootItemsStatus() {
		return getEventStatus(RetrieveBaseEventType.ROOT, null);
	}

	@Override
	public boolean isRootItemsDone() {
		return isEventDone(RetrieveBaseEventType.ROOT, null);
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
	protected Object doRetrieveServiceCall(final RetrieveEventType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseEventType.CHILDREN)) {
			return getDataApi().getChildren((T) criteria);
		}
		if (Objects.equals(type, RetrieveBaseEventType.ROOT)) {
			return getDataApi().getRootItems();
		}
		return super.doRetrieveServiceCall(type, criteria);
	}

}
