package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Default API Keyed Object for common fields.
 *
 * @author jonathan
 */
public class DefaultObject implements ApiKeyIdObject {

	private String id;
	private String businessKey;
	private String description;
	private boolean active = true;
	private boolean custom = true;
	private Timestamp timestamp;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;
	}

	@Override
	public String getBusinessKey() {
		return businessKey;
	}

	@Override
	public void setBusinessKey(final String businessKey) {
		this.businessKey = businessKey;
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
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(final boolean active) {
		this.active = active;
	}

	@Override
	public boolean isCustom() {
		return custom;
	}

	@Override
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

	@Override
	public String toString() {
		return description + " [" + businessKey + "]";
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + Objects.hashCode(this.id);
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
		final DefaultObject other = (DefaultObject) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

}
