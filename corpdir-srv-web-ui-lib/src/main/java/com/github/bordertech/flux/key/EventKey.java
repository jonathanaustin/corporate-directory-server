package com.github.bordertech.flux.key;

/**
 * Key used to identify dispatched events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class EventKey extends AbstractKey<EventType> {

	public EventKey(final EventType eventType) {
		this(eventType, null);
	}

	public EventKey(final String qualifier) {
		this(null, qualifier);
	}

	public EventKey(final EventType eventType, final String qualifier) {
		super(eventType, qualifier);
	}

}
