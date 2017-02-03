package com.github.bordertech.corpdir.api.data;

import java.io.Serializable;

/**
 * Position in organization.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Position implements Serializable {

	private Long id;
	private String alternateKey;
	private String desc;
	private boolean hasPositions;
	private boolean hasContacts;
	private boolean active;
	private boolean custom;

	/**
	 *
	 * @return the unique id
	 */
	public Long getId() {
		return id;
	}

	/**
	 *
	 * @param id the unique id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the alternate position key
	 */
	public String getAlternateKey() {
		return alternateKey;
	}

	/**
	 *
	 * @param alternateKey the alternate position key
	 */
	public void setAlternateKey(final String alternateKey) {
		this.alternateKey = alternateKey;
	}

	/**
	 *
	 * @return the description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 *
	 * @param desc the description
	 */
	public void setDesc(final String desc) {
		this.desc = desc;
	}

	/**
	 *
	 * @return true if manages positions
	 */
	public boolean isHasPositions() {
		return hasPositions;
	}

	/**
	 *
	 * @param hasPositions true if manages positions
	 */
	public void setHasPositions(final boolean hasPositions) {
		this.hasPositions = hasPositions;
	}

	/**
	 *
	 * @return true if has contacts
	 */
	public boolean isHasContacts() {
		return hasContacts;
	}

	/**
	 *
	 * @param hasContacts true if has contacts
	 */
	public void setHasContacts(final boolean hasContacts) {
		this.hasContacts = hasContacts;
	}

	/**
	 *
	 * @return true if active record
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active record
	 */
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @return true if custom record
	 */
	public boolean isCustom() {
		return custom;
	}

	/**
	 *
	 * @param custom true if custom record
	 */
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

}
