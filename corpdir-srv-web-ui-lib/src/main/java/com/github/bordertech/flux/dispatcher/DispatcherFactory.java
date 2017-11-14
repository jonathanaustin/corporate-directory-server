package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.util.Factory;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class DispatcherFactory {

	private static final Dispatcher DISPATCHER = Factory.newInstance(Dispatcher.class);

	private DispatcherFactory() {
	}

	public static Dispatcher getInstance() {
		if (UIContextHolder.getCurrent() == null) {
			throw new IllegalStateException("Cannot use the dispatcher without a user context");
		}
		return DISPATCHER;
	}

}
