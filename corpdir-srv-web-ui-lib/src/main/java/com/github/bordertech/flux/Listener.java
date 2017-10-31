package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * @param <T> the event type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Listener<T extends Event> extends Serializable {

	void handleEvent(final T event);

}
