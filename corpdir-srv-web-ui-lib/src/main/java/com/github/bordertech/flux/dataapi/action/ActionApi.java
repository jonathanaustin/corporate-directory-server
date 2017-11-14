package com.github.bordertech.flux.dataapi.action;

import com.github.bordertech.flux.dataapi.DataApi;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ActionApi<T> extends DataApi {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();

}
