package com.github.bordertech.flux;

import com.github.bordertech.flux.key.EventType;
import com.github.bordertech.flux.key.Keyable;
import com.github.bordertech.flux.key.StoreKey;

/**
 * Store the current state for views.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Store extends Keyable<StoreKey> {

	/**
	 * Register the event listeners.
	 */
	void registerListeners();

	/**
	 * Unregister the event listeners.
	 */
	void unregisterListeners();

	/**
	 * Dispatch the change event.
	 *
	 * @param eventType the event type that caused the change
	 */
	void dispatchChangeEvent(final EventType eventType);

	/**
	 * @return the attached dispatcher.
	 */
	Dispatcher getDispatcher();
}
