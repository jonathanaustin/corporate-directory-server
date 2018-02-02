package com.github.bordertech.flux.crud.view.consumer;

import com.github.bordertech.flux.crud.store.CrudStore;
import com.github.bordertech.flux.view.consumer.StoreConsumerByKey;

/**
 * View is a CRUD Entity Store Consumer.
 *
 * @param <S> the search criteria
 * @param <K> the item key type
 * @param <T> the item type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudStoreConsumer<S, K, T> extends StoreConsumerByKey<CrudStore<S, K, T>> {
}
