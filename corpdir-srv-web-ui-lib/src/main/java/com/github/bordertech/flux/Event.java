package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Event extends Serializable {

	EventKey getEventKey();

	Object getData();

	Exception getException();

}
