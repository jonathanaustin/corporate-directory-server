package com.github.bordertech.corpdir.jpa.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Position in organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Position")
public class PositionEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String alternateKey;
	private String description;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<PositionEntity> subPositions;
	@OneToMany(fetch = FetchType.LAZY)
	private List<ContactEntity> contacts;
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
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @param description the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 *
	 * @return the positions managed by this position
	 */
	public List<PositionEntity> getSubPositions() {
		return subPositions;
	}

	/**
	 *
	 * @param subPositions the positions managed by this position
	 */
	public void setSubPositions(final List<PositionEntity> subPositions) {
		this.subPositions = subPositions;
	}

	/**
	 *
	 * @return the contacts in this position
	 */
	public List<ContactEntity> getContacts() {
		return contacts;
	}

	/**
	 *
	 * @param contacts the contacts in this position
	 */
	public void setContacts(final List<ContactEntity> contacts) {
		this.contacts = contacts;
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
