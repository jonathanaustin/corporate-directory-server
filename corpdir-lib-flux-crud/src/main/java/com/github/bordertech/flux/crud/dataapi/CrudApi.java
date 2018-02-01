package com.github.bordertech.flux.crud.dataapi;

import com.github.bordertech.flux.dataapi.SearchKeyableApi;

/**
 * CRUD Entity API.
 *
 * @param <S> the search criteria
 * @param <K> the entity key type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudApi<S, K, T> extends SearchKeyableApi<S, K, T> {

	T retrieve(final K key);

	T create(final T item);

	T update(final T item);

	void delete(final T item);

	T createInstance();

}
