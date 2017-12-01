package com.github.bordertech.flux.wc.view.smart.consumer;

import com.github.bordertech.flux.crud.store.SearchStore;

/**
 * SearchStore Consumer.
 *
 * @author jonathan
 */
public interface SearchStoreConsumer<S, T> {

	void setSearchStoreKey(final String storeKey);

	String getSearchStoreKey();

	SearchStore<S, T> getSearchStore();
}
