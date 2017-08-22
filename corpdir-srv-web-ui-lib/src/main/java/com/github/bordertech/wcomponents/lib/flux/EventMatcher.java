package com.github.bordertech.wcomponents.lib.flux;

import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class EventMatcher implements Matcher {

	private final EventType eventType;
	private final String qualifier;

	public EventMatcher(final EventType eventType) {
		this(eventType, null);
	}

	public EventMatcher(final String qualifier) {
		this(null, qualifier);
	}

	public EventMatcher(final EventType eventType, final String qualifier) {
		if (eventType == null && qualifier == null) {
			throw new IllegalArgumentException("At least EventType or Qualifier must be provided.");
		}
		this.eventType = eventType;
		this.qualifier = qualifier;
	}

	@Override
	public EventType getEventType() {
		return eventType;
	}

	@Override
	public String getQualifier() {
		return qualifier;
	}

	/**
	 * Check if the qualifier and matcher are a match.
	 *
	 * @param qualifier the event qualifier to test if it matches
	 * @return true if a match
	 */
	@Override
	public boolean matches(final EventQualifier qualifier) {
		// Check for straight match
		if (Objects.equals(getQualifier(), qualifier.getQualifier())
				&& Objects.equals(getEventType(), qualifier.getEventType())) {
			return true;
		}
		// If matcher qualifier is null, check the types are the same
		if (getQualifier() == null && Objects.equals(getEventType(), qualifier.getEventType())) {
			return true;
		}
		// If matcher eventType is null, check the qualfiers are the same
		if (getEventType() == null && Objects.equals(getQualifier(), qualifier.getQualifier())) {
			return true;
		}
		return false;
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
		if (!(obj instanceof EventMatcher)) {
			return false;
		}
		final EventMatcher other = (EventMatcher) obj;
		if (!Objects.equals(this.qualifier, other.qualifier)) {
			return false;
		}
		if (!Objects.equals(this.eventType, other.eventType)) {
			return false;
		}
		return true;
	}

}
