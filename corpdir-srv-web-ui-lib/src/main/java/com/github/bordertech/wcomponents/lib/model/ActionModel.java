package com.github.bordertech.wcomponents.lib.model;

/**
 *
 * @author jonathan
 */
public interface ActionModel<T> extends Model {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T refresh(final T entity);

	T createInstance();

}
