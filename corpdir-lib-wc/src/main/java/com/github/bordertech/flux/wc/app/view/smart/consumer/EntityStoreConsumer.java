package com.github.bordertech.flux.wc.app.view.smart.consumer;

import com.github.bordertech.flux.crud.store.EntityStore;

/**
 * Entity Store Consumer.
 *
 * @author jonathan
 */
public interface EntityStoreConsumer<T> {

	void setEntityStoreKey(final String storeKey);

	String getEntityStoreKey();

	EntityStore<T> getEntityStore();
}
