package com.github.bordertech.flux.crud.actioncreator;

import com.github.bordertech.flux.ActionCreator;

/**
 * Provides the action creator interface to handle change store actions.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudActionCreator<T> extends ActionCreator {

	T create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	T createInstance();
}
