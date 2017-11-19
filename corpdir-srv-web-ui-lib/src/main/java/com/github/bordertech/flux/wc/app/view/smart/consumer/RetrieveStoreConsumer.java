package com.github.bordertech.flux.wc.app.view.smart.consumer;

import com.github.bordertech.flux.app.store.retrieve.RetrieveStore;

/**
 * Retrieve Store Consumer.
 *
 * @author jonathan
 */
public interface RetrieveStoreConsumer {

	void setRetrieveStoreKey(final String storeKey);

	String getRetrieveStoreKey();

	RetrieveStore getRetrieveStore();
}
