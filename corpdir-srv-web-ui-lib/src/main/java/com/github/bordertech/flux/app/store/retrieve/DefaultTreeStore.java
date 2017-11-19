package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.app.action.RetrieveActionType;
import com.github.bordertech.flux.app.action.base.ModifyTreeBaseActionType;
import com.github.bordertech.flux.app.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.app.dataapi.CrudTreeApi;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 * @param <T> the item type
 * @param <D> the data API type
 *
 * @author jonathan
 */
public class DefaultTreeStore<T, D extends CrudTreeApi<T>> extends DefaultEntityStore<T, D> implements TreeStore<T> {

	public DefaultTreeStore(final String storeKey, final D api) {
		super(storeKey, api);
	}

	@Override
	public void registerListeners() {

		// Action Listeners
		for (ModifyTreeBaseActionType type : ModifyTreeBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleModifyTreeBaseActions(action);
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
		return (List<T>) getActionResult(RetrieveBaseActionType.CHILDREN, item);
	}

	@Override
	public List<T> getRootItems() {
		return (List<T>) getActionResult(RetrieveBaseActionType.ROOT, null);
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
		return getActionStatus(RetrieveBaseActionType.CHILDREN, item);
	}

	@Override
	public boolean isChildrenDone(final T item) {
		return isActionDone(RetrieveBaseActionType.CHILDREN, item);
	}

	@Override
	public ServiceStatus getRootItemsStatus() {
		return getActionStatus(RetrieveBaseActionType.ROOT, null);
	}

	@Override
	public boolean isRootItemsDone() {
		return isActionDone(RetrieveBaseActionType.ROOT, null);
	}

	protected void handleModifyTreeBaseActions(final Action action) {
		ModifyTreeBaseActionType type = (ModifyTreeBaseActionType) action.getKey().getType();
		boolean changed = false;
		switch (type) {
			case ADD_CHILD:
				handleAddChildAction(action);
				changed = true;
				break;
			case REMOVE_CHILD:
				handleRemoveChildAction(action);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected void handleAddChildAction(final Action action) {
		// Create Action
	}

	protected void handleRemoveChildAction(final Action action) {
		// Update action
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseActionType.CHILDREN)) {
			return getDataApi().getChildren((T) criteria);
		}
		if (Objects.equals(type, RetrieveBaseActionType.ROOT)) {
			return getDataApi().getRootItems();
		}
		return super.doRetrieveServiceCall(type, criteria);
	}

}
