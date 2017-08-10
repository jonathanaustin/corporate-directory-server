package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Event<T> implements Serializable {

	private final View trigger;
	private final EventType eventType;
	private final T data;

	public Event(final View trigger, final EventType eventType) {
		this(trigger, eventType, null);
	}

	public Event(final View trigger, final EventType eventType, final T data) {
		this.trigger = trigger;
		this.eventType = eventType;
		this.data = data;
	}

	public View getTrigger() {
		return trigger;
	}

	public EventType getEventType() {
		return eventType;
	}

	public T getData() {
		return data;
	}

}
