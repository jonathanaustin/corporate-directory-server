package com.github.bordertech.flux.store.impl;

import com.github.bordertech.flux.dataapi.SearchKeyableApi;
import com.github.bordertech.flux.store.SearchKeyableStore;
import java.util.Set;

/**
 * Default search store with keyable item.
 *
 * @param <S> the criteria type
 * @param <K> the item key type
 * @param <T> the item type
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultSearchKeyableDataApiStore<S, K, T, D extends SearchKeyableApi<S, K, T>> extends DefaultSearchDataApiStore<S, T, D> implements SearchKeyableStore<S, K, T> {

	public DefaultSearchKeyableDataApiStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys, api);
	}

	@Override
	public K getItemKey(final T item) {
		return getDataApi().getItemKey(item);
	}

}
