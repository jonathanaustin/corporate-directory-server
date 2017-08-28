package com.github.bordertech.wcomponents.lib.model;

/**
 *
 * @author jonathan
 */
public interface ActionSubModel<T, S> extends ActionModel<T> {

	T addSub(final T parent, final S sub);

	T removeSub(final T parent, final S sub);

}
