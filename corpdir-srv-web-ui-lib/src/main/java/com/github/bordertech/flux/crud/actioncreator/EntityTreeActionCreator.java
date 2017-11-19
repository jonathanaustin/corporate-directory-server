package com.github.bordertech.flux.crud.actioncreator;

/**
 * Provides the action creator interface to handle change store actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface EntityTreeActionCreator<T> extends EntityActionCreator<T> {

	T addChild(final T parent, final T child);

	T removeChild(final T parent, final T child);
}
