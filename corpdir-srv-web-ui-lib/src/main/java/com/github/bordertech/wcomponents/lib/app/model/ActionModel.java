package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.flux.wc.dataapi.DataApi;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ActionModel<T> extends DataApi {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();

}
