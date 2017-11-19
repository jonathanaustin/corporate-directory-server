package com.github.bordertech.flux.wc.app.view.smart.consumer;

import com.github.bordertech.flux.app.store.retrieve.SearchStore;

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
