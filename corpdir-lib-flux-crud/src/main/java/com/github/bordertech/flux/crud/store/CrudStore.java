package com.github.bordertech.flux.crud.store;

import com.github.bordertech.flux.store.SearchKeyableStore;
import com.github.bordertech.taskmanager.service.CallType;
import com.github.bordertech.taskmanager.service.ResultHolder;

/**
 * CRUD Entity Store.
 *
 * @param <S> the search type
 * @param <K> the entity key type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0 *
 */
public interface CrudStore<S, K, T> extends SearchKeyableStore<S, K, T> {

	ResultHolder<K, T> fetch(final K entityKey, final CallType callType);
}
