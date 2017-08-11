package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class EventMatcher implements Serializable {

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

	public EventType getEventType() {
		return eventType;
	}

	public String getQualifier() {
		return qualifier;
	}

	/**
	 * Check if the qualifier and matcher are a match.
	 *
	 * @param qualifier the event qualifier to test if it matches
	 * @return true if a match
	 */
	public boolean matches(final EventQualifier qualifier) {
		return matches(this, qualifier);
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

	/**
	 * Check if the qualifier and matcher are a match.
	 *
	 * @param matcher the event matcher to test if it matches
	 * @param qualifier the event qualifier to test if it matches
	 * @return true if a match
	 */
	public static boolean matches(final EventMatcher matcher, final EventQualifier qualifier) {
		// Check for straight match
		if (Objects.equals(matcher.getQualifier(), qualifier.getQualifier()) && Objects.equals(matcher.getEventType(), qualifier.getEventType())) {
			return true;
		}
		// If either qualifier is null, check the types are the same
		if ((matcher.getQualifier() == null || qualifier.getQualifier() == null) && Objects.equals(matcher.getEventType(), qualifier.getEventType())) {
			return true;
		}
		// If either eventType is null, check the qualfiers are the same
		if ((matcher.getEventType() == null || qualifier.getEventType() == null) && Objects.equals(matcher.getQualifier(), qualifier.getQualifier())) {
			return true;
		}
		return false;
	}

}
