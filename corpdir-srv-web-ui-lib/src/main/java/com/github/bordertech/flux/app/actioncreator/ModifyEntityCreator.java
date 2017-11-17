package com.github.bordertech.flux.app.actioncreator;

/**
 * Provides the action (event) creator interface to handle change store events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ModifyEntityCreator<T> extends ModifyCreator {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();
}
