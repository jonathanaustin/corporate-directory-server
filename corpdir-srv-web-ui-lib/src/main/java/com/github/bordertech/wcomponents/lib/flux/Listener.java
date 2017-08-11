package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Listener<T extends Event> extends Serializable {

	void handleEvent(final T event);

}
