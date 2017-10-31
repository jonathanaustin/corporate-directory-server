package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Dumb View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface View extends Serializable {

	/**
	 * The default qualifier separator character.
	 */
	String QUALIFIER_CONTEXT_SEPERATOR = "_";

	/**
	 * A qualifier must start with a letter and followed by letters, digits or dash.
	 */
	String QUALIFIER_VALIDATION_PATTERN = "[a-zA-Z][0-9a-zA-Z-]*";

	/**
	 * @return the qualifier to be used on listeners or events (if needed)
	 */
	String getQualifier();

	/**
	 *
	 * @param qualifier the qualifier to be used on events
	 */
	void setQualifier(final String qualifier);

	/**
	 * @return the context qualifier
	 */
	String getFullQualifier();

	/**
	 * Register the store change listeners.
	 */
	void registerStoreChangeListeners();

	/**
	 * Unregister the event listeners.
	 */
	void unregisterStoreChangeListeners();

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	void dispatchEvent(final EventType eventType);

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	void dispatchEvent(final EventType eventType, final Object data);

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	void dispatchEvent(final EventType eventType, final Object data, final Exception exception);

	/**
	 * Helper method to dispatch an event for this view.
	 *
	 * @param event the event to dispatch
	 */
	void dispatchEvent(final Event event);

}
