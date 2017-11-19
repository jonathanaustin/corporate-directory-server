package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.app.action.RetrieveActionType;
import com.github.bordertech.flux.app.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.app.dataapi.CrudApi;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.Objects;

/**
 * @param <T> the item type
 * @param <D> the API data type
 *
 * @author jonathan
 */
public class DefaultRetrieveEntityStore<T, D extends CrudApi<T>> extends AbstractRetrieveDataApiStore<D> implements RetrieveEntityStore<T> {

	public DefaultRetrieveEntityStore(final String storeKey, final D api) {
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
		if (Objects.equals(type, RetrieveBaseActionType.SEARCH)) {
			return getDataApi().refresh((T) criteria);
		}
		throw new IllegalStateException("ACtion not supported [" + type + "].");
	}

}
