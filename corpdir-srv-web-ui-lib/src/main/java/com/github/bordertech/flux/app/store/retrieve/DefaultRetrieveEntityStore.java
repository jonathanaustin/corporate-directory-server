package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.app.action.RetrieveActionType;
import com.github.bordertech.flux.app.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.app.dataapi.CrudApi;
import com.github.bordertech.flux.key.StoreKey;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.Objects;

/**
 * @param <T> the item type
 * @param <D> the API data type
 *
 * @author jonathan
 */
public class DefaultRetrieveEntityStore<T, D extends CrudApi<T>> extends AbstractRetrieveDataApiStore<D> implements RetrieveEntityStore<T> {

	public DefaultRetrieveEntityStore(final StoreKey storeKey, final D api) {
		super(storeKey, api);
	}

	@Override
	public ServiceStatus getRetrieveStatus(final T key) {
		return getActionStatus(RetrieveBaseActionType.RETRIEVE, key);
	}

	@Override
	public boolean isRetrieveDone(final T key) {
		return isActionDone(RetrieveBaseActionType.RETRIEVE, key);
	}

	@Override
	public T retrieve(final T key) {
		// Retrieve and RetrieveAsync get treated the same
		return (T) getActionResult(RetrieveBaseActionType.RETRIEVE, key);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseActionType.SEARCH)) {
			return getDataApi().refresh((T) criteria);
		}
		throw new IllegalStateException("ACtion not supported [" + type + "].");
	}

}
