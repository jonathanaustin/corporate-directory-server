package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Provides the action creator interface to handle change store actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ActionCreator extends Serializable {

	/**
	 * @return the creator key (also used as its qualifier for actions).
	 */
	String getKey();

}
