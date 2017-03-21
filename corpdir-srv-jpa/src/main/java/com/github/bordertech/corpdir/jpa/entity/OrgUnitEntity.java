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
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "OrgUnit")
public class OrgUnitEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "parentOrgUnit_Id")
	private OrgUnitEntity parentOrgUnit;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentOrgUnit")
	private List<OrgUnitEntity> subOrgUnits;

	@OneToMany(fetch = FetchType.LAZY)
	private List<PositionEntity> positions;
	@OneToMany(fetch = FetchType.LAZY)
	private List<ContactEntity> contacts;

	@ManyToOne(fetch = FetchType.EAGER)
	private UnitTypeEntity type;

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
	 * @return the parent org unit
	 */
	public OrgUnitEntity getParentOrgUnit() {
		return parentOrgUnit;
	}

	/**
	 * @param parentOrgUnit the parent org unit
	 */
	public void setParentOrgUnit(final OrgUnitEntity parentOrgUnit) {
		this.parentOrgUnit = parentOrgUnit;
	}

	/**
	 *
	 * @return the alternate org unit key
	 */
	public String getAlternateKey() {
		return alternateKey;
	}

	/**
	 *
	 * @param alternateKey the alternate org unit key
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
	 * @return the organization type
	 */
	public UnitTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the organization type
	 */
	public void setType(final UnitTypeEntity type) {
		this.type = type;
	}

	/**
	 *
	 * @return the units managed by this unit
	 */
	public List<OrgUnitEntity> getSubOrgUnits() {
		return subOrgUnits;
	}

	/**
	 * Add a sub org unit.
	 *
	 * @param orgUnit the sub org unit to add
	 */
	public void addSubOrgUnit(final OrgUnitEntity orgUnit) {
		if (subOrgUnits == null) {
			subOrgUnits = new ArrayList<>();
		}
		subOrgUnits.add(orgUnit);
		orgUnit.setParentOrgUnit(this);
	}

	/**
	 * Remove a sub org unit. ]
	 *
	 * @param orgUnit the orgUnit to remove
	 */
	public void removeSubOrgUnit(final OrgUnitEntity orgUnit) {
		if (subOrgUnits != null) {
			subOrgUnits.remove(orgUnit);
		}
		orgUnit.setParentOrgUnit(null);
	}

	/**
	 *
	 * @return the positions belonging to this unit
	 */
	public List<PositionEntity> getPositions() {
		return positions;
	}

	/**
	 * Add a position.
	 *
	 * @param position the position to add
	 */
	public void addPosition(final PositionEntity position) {
		if (positions == null) {
			positions = new ArrayList<>();
		}
		positions.add(position);
	}

	/**
	 * Remove a position. ]
	 *
	 *
	 * @param position the position to remove
	 */
	public void removePosition(final PositionEntity position) {
		if (positions != null) {
			positions.remove(position);
		}
	}

	/**
	 *
	 * @return the contacts belonging to this unit
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
