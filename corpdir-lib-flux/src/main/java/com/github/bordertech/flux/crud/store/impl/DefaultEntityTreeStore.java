package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.EntityTreeActionBaseType;
import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;
import com.github.bordertech.flux.crud.store.EntityTreeStore;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @param <T> the item type
 * @param <D> the data API type
 *
 * @author jonathan
 */
public class DefaultEntityTreeStore<T, D extends CrudTreeApi<T>> extends DefaultEntityStore<T, D> implements EntityTreeStore<T> {

	public DefaultEntityTreeStore(final String storeKey, final String actionCreatorKey, final D api) {
		super(storeKey, actionCreatorKey, api);
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		super.registerListeners(ids);
		// Action Listeners
		for (EntityTreeActionBaseType type : EntityTreeActionBaseType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleModifyTreeBaseActions(action);
				}
			};
			String id = registerActionCreatorListener(type, listener);
			ids.add(id);
		}
	}

	@Override
	public boolean hasChildren(final T item) {
		return getDataApi().hasChildren(item);
	}

	@Override
	public List<T> getChildren(final T item) {
		return (List<T>) getActionResult(RetrieveActionBaseType.CHILDREN, item);
	}

	@Override
	public List<T> getRootItems() {
		return (List<T>) getActionResult(RetrieveActionBaseType.ROOT, null);
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
		return getAsyncProgressStatus(RetrieveActionBaseType.CHILDREN, item);
	}

	@Override
	public boolean isChildrenDone(final T item) {
		return isAsyncDone(RetrieveActionBaseType.CHILDREN, item);
	}

	@Override
	public ServiceStatus getRootItemsStatus() {
		return getAsyncProgressStatus(RetrieveActionBaseType.ROOT, null);
	}

	@Override
	public boolean isRootItemsDone() {
		return isAsyncDone(RetrieveActionBaseType.ROOT, null);
	}

	protected void handleModifyTreeBaseActions(final Action action) {
		ServiceUtil.clearCache(getStoreCache());
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveActionBaseType.CHILDREN)) {
			return getDataApi().getChildren((T) criteria);
		}
		if (Objects.equals(type, RetrieveActionBaseType.ROOT)) {
			return getDataApi().getRootItems();
		}
		return super.doRetrieveServiceCall(type, criteria);
	}

}
