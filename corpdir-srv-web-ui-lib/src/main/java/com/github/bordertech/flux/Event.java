package com.github.bordertech.flux;

import com.github.bordertech.flux.key.EventKey;
import com.github.bordertech.flux.key.Keyable;

/**
 * Event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Event extends Keyable<EventKey> {

	Object getData();

}
