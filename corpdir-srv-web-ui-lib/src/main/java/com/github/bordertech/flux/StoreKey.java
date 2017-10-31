package com.github.bordertech.flux;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class StoreKey implements Serializable {

	private final StoreType storeType;
	private final String qualifier;

	public StoreKey(final StoreType storeType) {
		this(storeType, null);
	}

	public StoreKey(final StoreType storeType, final String qualifier) {
		if (storeType == null) {
			throw new IllegalArgumentException("Store type must be provided.");
		}
		this.storeType = storeType;
		this.qualifier = qualifier;
	}

	public StoreType getStoreType() {
		return storeType;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + Objects.hashCode(this.storeType);
		hash = 79 * hash + Objects.hashCode(this.qualifier);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof StoreKey)) {
			return false;
		}
		final StoreKey other = (StoreKey) obj;
		if (!Objects.equals(this.qualifier, other.qualifier)) {
			return false;
		}
		if (!Objects.equals(this.storeType, other.storeType)) {
			return false;
		}
		return true;
	}

}
