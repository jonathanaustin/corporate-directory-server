package com.github.bordertech.flux.app.actioncreator;

/**
 * Provides the action creator interface to handle change store actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ModifyTreeCreator<T> extends ModifyEntityCreator<T> {

	T addChild(final T parent, final T child);

	T removeChild(final T parent, final T child);
}
