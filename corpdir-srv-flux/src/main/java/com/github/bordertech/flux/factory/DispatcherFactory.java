package com.github.bordertech.flux.factory;



import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.locator.BindingFactory;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class DispatcherFactory {

	private static final Dispatcher DISPATCHER = BindingFactory.newInstance(Dispatcher.class);

	private DispatcherFactory() {
	}

	public static Dispatcher getInstance() {
		return DISPATCHER;
	}

}
