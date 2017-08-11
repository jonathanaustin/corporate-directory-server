package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Event<T> implements Serializable {

	private final View trigger;
	private final EventQualifier eventQualifier;
	private final T data;

	public Event(final EventType eventType) {
		this(null, eventType, null);
	}

	public Event(final View trigger, final EventType eventType) {
		this(trigger, eventType, null);
	}

	public Event(final View trigger, final EventType eventType, final T data) {
		this(trigger, new EventQualifier(eventType, trigger == null ? null : trigger.getQualifier()), data);
	}

	public Event(final View trigger, final EventQualifier eventQualifier) {
		this(trigger, eventQualifier, null);
	}

	public Event(final View trigger, final EventQualifier eventQualifier, final T data) {
		if (eventQualifier == null) {
			throw new IllegalArgumentException("An event qualifier must be provided.");
		}
		if (eventQualifier.getEventType() == null) {
			throw new IllegalArgumentException("An event type must be provided.");
		}
		this.trigger = trigger;
		this.eventQualifier = eventQualifier;
		this.data = data;
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

	public T getData() {
		return data;
	}

}
