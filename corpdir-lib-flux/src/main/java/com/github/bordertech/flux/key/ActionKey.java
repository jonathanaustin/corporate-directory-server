package com.github.bordertech.flux.key;

import java.io.Serializable;
import java.util.Objects;

/**
 * Action key.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ActionKey implements Serializable {

	private final ActionType type;
	private final String qualifier;

	public ActionKey(final ActionType type) {
		this(type, null);
	}
	
	public ActionKey(final ActionType type, final String qualifier) {
		if (type == null) {
			throw new IllegalArgumentException("Key Type must be provided.");
		}
		this.type = type;
		this.qualifier = qualifier;
	}

	public ActionType getType() {
		return type;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + Objects.hashCode(this.type);
		hash = 79 * hash + Objects.hashCode(this.qualifier);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ActionKey)) {
			return false;
		}
		final ActionKey other = (ActionKey) obj;
		if (!Objects.equals(this.qualifier, other.qualifier)) {
			return false;
		}
		if (!Objects.equals(this.type, other.type)) {
			return false;
		}
		return true;
	}

}
