package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.key.EventKey;
import com.github.bordertech.flux.key.EventType;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEvent implements Event {

	private final EventKey eventKey;
	private final Object data;

	public DefaultEvent(final EventType eventType) {
		this(eventType, null);
	}

	public DefaultEvent(final EventType eventType, final Object data) {
		this(eventType, null, data);
	}

	public DefaultEvent(final EventType eventType, final String qualifier, final Object data) {
		this(new EventKey(eventType, qualifier), data);
	}

	public DefaultEvent(final EventKey eventKey, final Object data) {
		if (eventKey == null) {
			throw new IllegalArgumentException("An event key must be provided.");
		}
		if (eventKey.getType() == null) {
			throw new IllegalArgumentException("An event type must be provided.");
		}
		this.eventKey = eventKey;
		this.data = data;
	}

	@Override
	public EventKey getKey() {
		return eventKey;
	}

	@Override
	public Object getData() {
		return data;
	}

}
