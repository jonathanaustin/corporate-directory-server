package com.github.bordertech.corpdir.api.common;

import java.util.Objects;

/**
 * Default API Keyed Object for common fields.
 *
 * @author jonathan
 */
public class DefaultKeyIdObject extends DefaultIdObject implements ApiKeyIdObject {

	private String businessKey;
	private boolean active = true;
	private boolean custom = true;

	protected DefaultKeyIdObject() {
	}

	public DefaultKeyIdObject(final String id) {
		super(id);
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
		return getDescription() + " [" + getBusinessKey() + "]";
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
		final DefaultKeyIdObject other = (DefaultKeyIdObject) obj;
		if (!Objects.equals(getId(), other.getId())) {
			return false;
		}
		return true;
	}

}
