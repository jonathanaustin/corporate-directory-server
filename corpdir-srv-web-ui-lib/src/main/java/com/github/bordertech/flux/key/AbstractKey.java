package com.github.bordertech.flux.key;

import java.util.Objects;

/**
 * Abstract flux Key.
 *
 * @param <K> the key type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractKey<K extends KeyType> implements Key<K> {

	private final K type;
	private final String qualifier;

	public AbstractKey(final K type, final String qualifier) {
		if (type == null) {
			throw new IllegalArgumentException("Key Type must be provided.");
		}
		this.type = type;
		// For types with a ID, the qualifier is a combintaion of the qualifier and key type id.
		if (type instanceof KeyTypeId) {
			String id = ((KeyTypeId) type).getTypeId();
			String suffix = qualifier == null ? "" : "-" + qualifier;
			this.qualifier = id + suffix;
		} else {
			this.qualifier = qualifier;
		}
	}

	@Override
	public K getType() {
		return type;
	}

	@Override
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
		if (!(obj instanceof AbstractKey)) {
			return false;
		}
		final AbstractKey other = (AbstractKey) obj;
		if (!Objects.equals(this.qualifier, other.qualifier)) {
			return false;
		}
		if (!Objects.equals(this.type, other.type)) {
			return false;
		}
		return true;
	}

}
