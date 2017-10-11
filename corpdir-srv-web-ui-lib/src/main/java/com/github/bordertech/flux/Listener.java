package com.github.bordertech.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @param <T> the event type
 * @since 1.0.0
 */
public interface Listener<T extends Event> extends Serializable {

	void handleEvent(final T event);

}
