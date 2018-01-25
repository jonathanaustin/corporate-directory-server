package com.github.bordertech.flux.crud.dataapi;

import com.github.bordertech.flux.DataApi;

/**
 * Entity crud API.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface CrudApi<T> extends DataApi {

	T retrieve(final T entity);

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();

}
