package com.github.bordertech.flux.wc.view.smart.consumer;

import com.github.bordertech.flux.crud.store.RetrieveActionStore;

/**
 * Retrieve Store Consumer.
 *
 * @author jonathan
 */
public interface RetrieveStoreConsumer {

	void setRetrieveStoreKey(final String storeKey);

	String getRetrieveStoreKey();

	RetrieveActionStore getRetrieveStore();
}
