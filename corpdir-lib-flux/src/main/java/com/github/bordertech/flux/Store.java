package com.github.bordertech.flux;

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
	 * @return the attached dispatcher.
	 */
	Dispatcher getDispatcher();

	/**
	 * @return the action creators this store will listen to
	 */
	Set<String> getActionCreatorKeys();

}
