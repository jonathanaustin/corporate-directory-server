package com.github.bordertech.corpdir.jpa.common;

import javax.persistence.MappedSuperclass;
import com.github.bordertech.corpdir.jpa.common.feature.PersistActivable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistCustomable;

/**
 * Default persistent keyed object with custom and active flags.
 *
 * @author jonathan
 */
@MappedSuperclass
public class DefaultIdCustomObject extends DefaultIdObject implements PersistActivable, PersistCustomable {

	private boolean active = true;
	private boolean custom = true;

	/**
	 * Default constructor.
	 */
	protected DefaultIdCustomObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public DefaultIdCustomObject(final Long id) {
		super(id);
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
