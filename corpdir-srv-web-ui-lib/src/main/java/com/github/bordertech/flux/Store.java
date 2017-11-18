package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionType;
import com.github.bordertech.flux.key.Keyable;
import com.github.bordertech.flux.key.StoreKey;

/**
 * Store the current state for views.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Store extends Keyable<StoreKey> {

	/**
	 * Register the action listeners.
	 */
	void registerListeners();

	/**
	 * Unregister the action listeners.
	 */
	void unregisterListeners();

	/**
	 * Dispatch the change action.
	 *
	 * @param actionType the action type that caused the change
	 */
	void dispatchChangeAction(final ActionType actionType);

	/**
	 * @return the attached dispatcher.
	 */
	Dispatcher getDispatcher();
}
