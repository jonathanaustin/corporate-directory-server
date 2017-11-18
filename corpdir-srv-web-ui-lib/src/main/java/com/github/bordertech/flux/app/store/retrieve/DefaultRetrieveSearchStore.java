package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.app.action.RetrieveActionType;
import com.github.bordertech.flux.app.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.app.dataapi.SearchApi;
import com.github.bordertech.flux.key.StoreKey;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 * Default search store.
 *
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <D> the data API type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultRetrieveSearchStore<S, T, D extends SearchApi<S, T>> extends AbstractRetrieveDataApiStore<D> implements RetrieveSearchStore<S, T> {

	public DefaultRetrieveSearchStore(final StoreKey storeKey, final D api) {
		super(storeKey, api);
	}

	@Override
	public ServiceStatus getSearchStatus(final S criteria) {
		return getActionStatus(RetrieveBaseActionType.SEARCH, criteria);
	}

	@Override
	public boolean isSearchDone(final S criteria) {
		return isActionDone(RetrieveBaseActionType.SEARCH, criteria);
	}

	@Override
	public List<T> search(final S criteria) {
		return (List<T>) getActionResult(RetrieveBaseActionType.SEARCH, criteria);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseActionType.RETRIEVE)) {
			return getDataApi().search((S) criteria);
		}
		throw new IllegalStateException("Action not supported [" + type + "].");
	}

}
