package com.github.bordertech.flux.util;

import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;

/**
 *
 * @author jonathan
 */
public class FluxUtil {

	private FluxUtil() {
	}

	public static Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	public static <T extends ActionCreator> T getActionCreator(final String key) {
		return (T) getDispatcher().getActionCreator(key);
	}

	public static <T extends Store> T getStore(final String key) {
		return (T) getDispatcher().getStore(key);
	}

}
