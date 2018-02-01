package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Action Creators are collections of methods that are called to send actions to the Dispatcher.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ActionCreator extends Serializable {

	/**
	 * @return the creator key (also used as its qualifier for actions).
	 */
	String getKey();

	/**
	 * @return the attached dispatcher.
	 */
	Dispatcher getDispatcher();

}
