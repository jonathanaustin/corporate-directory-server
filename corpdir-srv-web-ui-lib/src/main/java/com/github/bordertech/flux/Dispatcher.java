package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionKey;
import java.io.Serializable;

/**
 * Action dispatcher.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Dispatcher extends Serializable {

	void dispatch(final Action action);

	String registerListener(final ActionKey matcher, final Listener listener);

	void unregisterListener(final String registerId);

	boolean isDispatching();

	Listener getListener(final String registerId);

	void registerStore(final Store store);

	void unregisterStore(final String key);

	Store getStore(final String key);

	void registerActionCreator(final ActionCreator creator);

	void unregisterActionCreator(final String key);

	ActionCreator getActionCreator(final String key);

}
