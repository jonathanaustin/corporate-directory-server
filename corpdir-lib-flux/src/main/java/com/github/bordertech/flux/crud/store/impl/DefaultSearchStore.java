package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.dataapi.SearchApi;
import com.github.bordertech.flux.crud.store.SearchStore;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
public class DefaultSearchStore<S, T, D extends SearchApi<S, T>> extends AbstractRetrieveDataApiStore<D> implements SearchStore<S, T> {

	public DefaultSearchStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys, api);
	}

	@Override
	public boolean isSearchDone(final S criteria) {
		return isAsyncDone(RetrieveActionBaseType.SEARCH, criteria);
	}

	@Override
	public List<T> search(final S criteria) {
		return (List<T>) getActionResult(RetrieveActionBaseType.SEARCH, criteria);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria) {
		if (Objects.equals(type, RetrieveActionBaseType.SEARCH)) {
			return getDataApi().search((S) criteria);
		}
		throw new IllegalStateException("Action not supported [" + type + "].");
	}

}
