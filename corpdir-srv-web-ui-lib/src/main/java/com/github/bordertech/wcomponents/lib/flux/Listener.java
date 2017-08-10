package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class Listener implements Serializable {

	private final String id = UUID.randomUUID().toString();

	private final EventType eventType;

	public Listener(final EventType eventType) {
		this.eventType = eventType;
	}

	public String getId() {
		return id;
	}

	public EventType getEventType() {
		return eventType;
	}

	abstract void handleEvent(final Event event);

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Listener && Objects.equals(((Listener) obj).id, id);
	}

}
