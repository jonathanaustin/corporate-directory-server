package com.github.bordertech.wcomponents.lib.model;

import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface ActionModel<T> extends Serializable {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T refresh(final T entity);

	T createInstance();

}
