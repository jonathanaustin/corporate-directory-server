package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionType;
import java.io.Serializable;
import java.util.Set;

/**
 * Stores manage application state for a particular domain.
 * <p>
 * A Store only changes it state when it receives an action from the Dispatcher. Stores emit change events to let
 * registered Views know when the state has changed.
 * </p>
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
	 *
	 * @param ids the set of ids
	 */
	void registerListeners(final Set<String> ids);

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
