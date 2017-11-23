package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionType;
import java.io.Serializable;
import java.util.Set;

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
	 * Register the store listeners.
	 */
	Set<String> registerListeners();

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
