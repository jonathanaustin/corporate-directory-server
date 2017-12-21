package com.github.bordertech.corpdir.api.common;

import java.util.Objects;

/**
 * Default API ID Object with Active and Custom fields.
 *
 * @author jonathan
 */
public class DefaultIdCustomObject extends DefaultIdObject implements ApiCustomable, ApiActivable {

	private boolean active = true;
	private boolean custom = true;

	protected DefaultIdCustomObject() {
	}

	public DefaultIdCustomObject(final String id) {
		super(id);
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
		final DefaultIdCustomObject other = (DefaultIdCustomObject) obj;
		if (!Objects.equals(getId(), other.getId())) {
			return false;
		}
		return true;
	}

}
