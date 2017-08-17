package com.github.bordertech.wcomponents.lib.app.model;

/**
 *
 * @author jonathan
 */
public interface UpdateSubModel<T, S> extends UpdateModel<T> {

	T addSub(final T parent, final S sub);

	T removeSub(final T parent, final S sub);

}
