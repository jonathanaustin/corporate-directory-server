package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Event implements Serializable {

	private final View trigger;
	private final EventQualifier eventQualifier;
	private final Object data;
	private final Exception exception;

	public Event(final EventType eventType) {
		this(null, eventType, null, null);
	}

	public Event(final EventType eventType, final Object data) {
		this(null, eventType, data, null);
	}

	public Event(final EventType eventType, final Object data, final Exception exception) {
		this(null, eventType, data, exception);
	}

	public Event(final View trigger, final EventType eventType, final Object data, final Exception exception) {
		this(trigger, new EventQualifier(eventType, trigger == null ? null : trigger.getQualifier()), data, exception);
	}

	public Event(final View trigger, final EventQualifier eventQualifier, final Object data, final Exception exception) {
		if (eventQualifier == null) {
			throw new IllegalArgumentException("An event qualifier must be provided.");
		}
		if (eventQualifier.getEventType() == null) {
			throw new IllegalArgumentException("An event type must be provided.");
		}
		this.trigger = trigger;
		this.eventQualifier = eventQualifier;
		this.data = data;
		this.exception = exception;
	}

	public View getTrigger() {
		return trigger;
	}

	public EventType getEventType() {
		return eventQualifier.getEventType();
	}

	public String getQualifier() {
		return eventQualifier.getQualifier();
	}

	public EventQualifier getEventQualifier() {
		return eventQualifier;
	}

	public Object getData() {
		return data;
	}

	public Exception getException() {
		return exception;
	}

}
