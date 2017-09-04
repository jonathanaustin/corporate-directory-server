package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import javax.persistence.MappedSuperclass;

/**
 * Default persistent keyed object.
 *
 * @author jonathan
 */
@MappedSuperclass
public class DefaultKeyIdObject extends DefaultIdObject implements PersistKeyIdObject {

	private String businessKey;
	private boolean active = true;
	private boolean custom = true;

	/**
	 * Default constructor.
	 */
	protected DefaultKeyIdObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public DefaultKeyIdObject(final Long id) {
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

	/**
	 *
	 * @return true if active record
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active record
	 */
	@Override
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @return true if custom record
	 */
	@Override
	public boolean isCustom() {
		return custom;
	}

	/**
	 *
	 * @param custom true if custom record
	 */
	@Override
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

}
