package com.github.bordertech.corpdir.api.data;

import java.util.Date;

/**
 * Abstract API object for common fields.
 *
 * @author jonathan
 */
public abstract class AbstractApiObject implements ApiObject {

	private String id;
	private String businessKey;
	private boolean active = true;
	private boolean custom = true;
	private Date version;

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
	public Date getVersion() {
		return version;
	}

	@Override
	public void setVersion(final Date version) {
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
