package com.github.bordertech.flux.crud.store.retrieve;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.EntityActionType;
import com.github.bordertech.flux.crud.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.Objects;

/**
 * Entity Store.
 *
 * @param <T> the item type
 * @param <D> the API data type
 *
 * @author jonathan
 */
public class DefaultEntityStore<T, D extends CrudApi<T>> extends AbstractRetrieveDataApiStore<D> implements EntityStore<T> {

	public DefaultEntityStore(final String storeKey, final D api) {
		super(storeKey, api);
	}

	@Override
	public ServiceStatus getFetchStatus(final T key) {
		return getActionStatus(RetrieveBaseActionType.FETCH, key);
	}

	@Override
	public boolean isFetchDone(final T key) {
		return isActionDone(RetrieveBaseActionType.FETCH, key);
	}

	@Override
	public T fetch(final T key) {
		// Retrieve and RetrieveAsync get treated the same
		return (T) getActionResult(RetrieveBaseActionType.FETCH, key);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseActionType.FETCH)) {
			return getDataApi().retrieve((T) criteria);
		}
		throw new IllegalStateException("Action not supported [" + type + "].");
	}

	@Override
	protected void handleModifyBaseActions(final Action action) {
		super.handleModifyBaseActions(action);
		EntityActionType type = (EntityActionType) action.getKey().getType();
		boolean changed = false;
		switch (type) {
			case CREATE:
			case UPDATE:
				handleCreateUpdateAction((T) action.getData());
				changed = true;
				break;
			case DELETE:
				handleDeleteAction((T) action.getData());
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected void handleCreateUpdateAction(final T entity) {
		// Update the result cache
		String key = getResultCacheKey(RetrieveBaseActionType.FETCH, entity);
		ResultHolder result = new ResultHolder(entity, entity);
		ServiceUtil.setResultHolder(key, result);
	}

	protected void handleDeleteAction(final T entity) {
		// Clear the result cache
		String key = getResultCacheKey(RetrieveBaseActionType.FETCH, entity);
		ServiceUtil.clearResult(key);
	}

}
