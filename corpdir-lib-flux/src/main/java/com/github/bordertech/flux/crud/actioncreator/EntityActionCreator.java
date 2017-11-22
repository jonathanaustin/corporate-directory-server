package com.github.bordertech.flux.crud.actioncreator;

/**
 * Provides the action creator interface to handle change store actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface EntityActionCreator<T> extends ModifyActionCreator {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();
}
