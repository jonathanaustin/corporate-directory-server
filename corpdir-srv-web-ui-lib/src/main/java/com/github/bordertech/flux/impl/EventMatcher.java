package com.github.bordertech.flux.impl;

import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.Matcher;
import com.github.bordertech.flux.Qualifier;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class EventMatcher implements Matcher {

	private final Qualifier matchQualifier;

	public EventMatcher(final EventType eventType) {
		this(eventType, null);
	}

	public EventMatcher(final String qualifier) {
		this(null, qualifier);
	}

	public EventMatcher(final EventType eventType, final String qualifier) {
		this(new EventQualifier(eventType, qualifier));
	}

	public EventMatcher(final EventQualifier matchQualifier) {
		this.matchQualifier = matchQualifier;
	}

	@Override
	public Qualifier getMatchQualifier() {
		return matchQualifier;
	}

	/**
	 * Check if the qualifier and matcher are a match.
	 *
	 * @param qualifier the event qualifier to test if it matches
	 * @return true if a match
	 */
	@Override
	public boolean matches(final Qualifier qualifier) {
		// Check for straight match
		if (Objects.equals(getMatchQualifier().getQualifier(), qualifier.getQualifier())
				&& Objects.equals(getMatchQualifier().getEventType(), qualifier.getEventType())) {
			return true;
		}
		// If matcher qualifier is null, check the types are the same
		if (getMatchQualifier().getQualifier() == null && Objects.equals(getMatchQualifier().getEventType(), qualifier.getEventType())) {
			return true;
		}
		// If matcher eventType is null, check the qualfiers are the same
		if (getMatchQualifier().getEventType() == null && Objects.equals(getMatchQualifier().getQualifier(), qualifier.getQualifier())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.matchQualifier.hashCode();
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
		return Objects.equals(this.matchQualifier, other.matchQualifier);
	}

}
