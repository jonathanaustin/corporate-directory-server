package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionType;
import java.io.Serializable;

/**
 * Store the current state for views.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Store extends Serializable {

	/**
	 * @return the store key (also used as its qualifier for actions).
	 */
	String getKey();

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
