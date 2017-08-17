package com.github.bordertech.wcomponents.lib.app.model;

import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface UpdateModel<T> extends Serializable {

	T save(final T entity);

	T update(final T entity);

	void delete(final T entity);

}
