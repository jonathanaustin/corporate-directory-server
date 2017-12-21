package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.flux.crud.store.EntityStore;
import com.github.bordertech.flux.crud.store.RetrieveActionException;
import com.github.bordertech.taskmanager.service.AsyncException;
import java.util.Objects;
import java.util.Set;

/**
 * Entity Store.
 *
 * @param <T> the item type
 * @param <D> the API data type
 *
 * @author jonathan
 */
public class DefaultEntityStore<T, D extends CrudApi<T>> extends AbstractRetrieveDataApiStore<D> implements EntityStore<T> {

	public DefaultEntityStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys, api);
	}

	@Override
	public boolean isFetchDone(final T key) throws AsyncException {
		return isAsyncDone(RetrieveActionBaseType.FETCH, key);
	}

	@Override
	public T fetch(final T key) throws RetrieveActionException {
		// Retrieve and RetrieveAsync get treated the same
		return (T) getActionResult(RetrieveActionBaseType.FETCH, key);
	}

	@Override
	protected Object doRetrieveDataApiCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveActionBaseType.FETCH)) {
			return getDataApi().retrieve((T) criteria);
		}
		throw new IllegalStateException("Action not supported [" + type + "].");
	}

}
