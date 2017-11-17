package com.github.bordertech.flux.app.dataapi;

/**
 * Entity crud API.
 *
 * @author jonathan
 * @param <S> the search criteria type
 * @param <T> the entity type
 * @param <K> the entity key
 */
public interface CrudApi<S, T, K> extends SearchApi<S, T> {

	T retrieve(final K key);

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();

}
