package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Dispatcher extends Serializable {

	void dispatch(final Event event);

	String register(final Listener listener, final Matcher matcher);

	void unregister(final String registerId);

	boolean isDispatching();

	Listener getListener(final String registerId);

}
