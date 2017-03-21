package com.github.bordertech.corpdir.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name = "parentPosition_Id")
	private PositionEntity parentPosition;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPosition")
	private List<PositionEntity> subPositions;

	@OneToMany(fetch = FetchType.LAZY)
	private List<ContactEntity> contacts;

	private String alternateKey;
	private String description;
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
	 * @return the parent position
	 */
	public PositionEntity getParentPosition() {
		return parentPosition;
	}

	/**
	 * @param parentPosition the parent position
	 */
	public void setParentPosition(final PositionEntity parentPosition) {
		this.parentPosition = parentPosition;
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
	 * Add a sub position.
	 *
	 * @param position the position to add
	 */
	public void addSubPosition(final PositionEntity position) {
		if (subPositions == null) {
			subPositions = new ArrayList<>();
		}
		subPositions.add(position);
		position.setParentPosition(this);
	}

	/**
	 * Remove a sub position. ]
	 *
	 *
	 * @param position the position to remove
	 */
	public void removePosition(final PositionEntity position) {
		if (subPositions != null) {
			subPositions.remove(position);
		}
		position.setParentPosition(null);
	}

	/**
	 *
	 * @return the contacts in this position
	 */
	public List<ContactEntity> getContacts() {
		return contacts;
	}

	/**
	 * Add a contact.
	 *
	 * @param contact the contact to add
	 */
	public void addContact(final ContactEntity contact) {
		if (contacts == null) {
			contacts = new ArrayList<>();
		}
		contacts.add(contact);
	}

	/**
	 * Remove a contact. ]
	 *
	 * @param contact the contact to remove
	 */
	public void removeContact(final ContactEntity contact) {
		if (contacts != null) {
			contacts.remove(contact);
		}
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
