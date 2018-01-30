package com.github.bordertech.flux.view.consumer;

import com.github.bordertech.flux.store.SearchStore;

/**
 * View is a SearchStore Consumer.
 *
 * @param <S> the search criteria
 * @param <K> the item key type
 * @param <T> the item type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface SearchStoreConsumer<S, K, T> extends StoreConsumer {

	void setSearchStoreKey(final String storeKey);

	String getSearchStoreKey();

	SearchStore<S, K, T> getSearchStore();
}
