package com.github.bordertech.flux.crud.actioncreator;

/**
 * Provides the action creator interface to handle change store actions.
 *
 * @param <T> the item type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudTreeActionCreator<T> extends CrudActionCreator<T> {

	T addChild(final T parent, final T child);

	T removeChild(final T parent, final T child);
}
