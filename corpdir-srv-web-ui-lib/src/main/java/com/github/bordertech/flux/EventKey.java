package com.github.bordertech.flux;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class EventKey implements Serializable {

	private final EventType eventType;
	private final String qualifier;

	public EventKey(final EventType eventType) {
		this(eventType, null);
	}

	public EventKey(final String qualifier) {
		this(null, qualifier);
	}

	public EventKey(final EventType eventType, final String qualifier) {
		if (eventType == null && qualifier == null) {
			throw new IllegalArgumentException("At least EventType or Qualifier must be provided.");
		}
		this.eventType = eventType;
		this.qualifier = qualifier;
	}

	public EventType getEventType() {
		return eventType;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + Objects.hashCode(this.eventType);
		hash = 79 * hash + Objects.hashCode(this.qualifier);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EventKey)) {
			return false;
		}
		final EventKey other = (EventKey) obj;
		if (!Objects.equals(this.qualifier, other.qualifier)) {
			return false;
		}
		if (!Objects.equals(this.eventType, other.eventType)) {
			return false;
		}
		return true;
	}

}
