package com.github.bordertech.flux.view.consumer;

import com.github.bordertech.flux.Store;

/**
 * View is a Store Consumer.
 *
 * @param <S> the store type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface StoreConsumerByKey<S extends Store> extends StoreConsumer {

	void setStoreKey(final String storeKey);

	String getStoreKey();

	S getStoreByKey();
}
