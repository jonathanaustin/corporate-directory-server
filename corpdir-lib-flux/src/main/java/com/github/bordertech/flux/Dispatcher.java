package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionKey;
import java.io.Serializable;

/**
 * Receives actions and broadcasts them to registered listeners.
 *
 * <p>
 * The dispatcher is the central hub that manages all data flow in a Flux application. It is essentially a registry of
 * listeners and has no real intelligence of its own — it is a simple mechanism for distributing the actions.
 * </p>
 * <p>
 * There is only one Dispatcher in an Application.
 * </p>
 * <p>
 * Some FLUX dispatcher implementations broadcast an Action to ALL registered listeners and allow the listeners to be
 * called in a particular order. At the moment, only matching listeners are called and there is no mechanism for
 * specifying an order.
 * </p>
 *
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

	void unregisterStore(final String storeKey);

	Store getStore(final String storeKey);

	void registerActionCreator(final ActionCreator creator);

	void unregisterActionCreator(final String creatorKey);

	ActionCreator getActionCreator(final String creatorKey);

}
