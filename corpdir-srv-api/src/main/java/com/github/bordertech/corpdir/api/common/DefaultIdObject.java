package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Default API Object for common fields.
 *
 * @author jonathan
 */
public class DefaultIdObject implements ApiIdObject {

	private String id;
	private String description;
	private Timestamp timestamp;

	protected DefaultIdObject() {
	}

	public DefaultIdObject(final String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	protected void setId(final String id) {
		this.id = id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public Timestamp getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(final Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return getDescription();
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + Objects.hashCode(getId());
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DefaultIdObject other = (DefaultIdObject) obj;
		if (!Objects.equals(getId(), other.getId())) {
			return false;
		}
		return true;
	}

}
