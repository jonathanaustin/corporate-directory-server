package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;

/**
 * Abstract API Keyed Object for common fields.
 *
 * @author jonathan
 */
public abstract class AbstractApiKeyIdObject implements ApiKeyIdObject {

	private String id;
	private String businessKey;
	private String description;
	private boolean active = true;
	private boolean custom = true;
	private Timestamp version;

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
	public Timestamp getVersion() {
		return version;
	}

	@Override
	public void setVersion(final Timestamp version) {
		this.version = version;
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

}
