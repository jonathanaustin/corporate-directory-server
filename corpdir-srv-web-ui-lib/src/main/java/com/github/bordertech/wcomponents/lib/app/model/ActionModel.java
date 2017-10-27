package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.wcomponents.lib.mvc.Model;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ActionModel<T> extends Model {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T refresh(final T entity);

	T createInstance();

}
