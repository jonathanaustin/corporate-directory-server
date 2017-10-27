package com.github.bordertech.flux.impl;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.Qualifier;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEvent implements Event {

	private final Qualifier qualifier;
	private final Object data;
	private final Exception exception;

	public DefaultEvent(final EventType eventType) {
		this(eventType, null);
	}

	public DefaultEvent(final EventType eventType, final Object data) {
		this(eventType, data, null);
	}

	public DefaultEvent(final EventType eventType, final Object data, final Exception exception) {
		this(eventType, null, data, exception);
	}

	public DefaultEvent(final EventType eventType, final String qualifier, final Object data, final Exception exception) {
		this(new EventQualifier(eventType, qualifier), data, exception);
	}

	public DefaultEvent(final Qualifier eventQualifier, final Object data, final Exception exception) {
		if (eventQualifier == null) {
			throw new IllegalArgumentException("An event qualifier must be provided.");
		}
		if (eventQualifier.getEventType() == null) {
			throw new IllegalArgumentException("An event type must be provided.");
		}
		this.qualifier = eventQualifier;
		this.data = data;
		this.exception = exception;
	}

	@Override
	public Qualifier getQualifier() {
		return qualifier;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public Exception getException() {
		return exception;
	}

}
