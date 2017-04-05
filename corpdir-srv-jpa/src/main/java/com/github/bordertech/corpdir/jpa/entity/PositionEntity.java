package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.AbstractPersistentKeyIdObject;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
public class PositionEntity extends AbstractPersistentKeyIdObject {

	@ManyToOne
	private PositionTypeEntity type;

	@ManyToOne
	@JoinColumn(name = "parentPosition_Id")
	private PositionEntity parentPosition;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPosition")
	private Set<PositionEntity> subPositions;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<OrgUnitEntity> manageOrgUnits;

	@ManyToOne
	private OrgUnitEntity orgUnit;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<ContactEntity> contacts;

	/**
	 * Default constructor.
	 */
	protected PositionEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public PositionEntity(final Long id) {
		super(id);
	}

	/**
	 *
	 * @return the position type
	 */
	public PositionTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the position type
	 */
	public void setType(final PositionTypeEntity type) {
		this.type = type;
	}

	/**
	 * @return the position this position reports to
	 */
	public PositionEntity getParentPosition() {
		return parentPosition;
	}

	/**
	 * @param parentPosition the position this position reports to
	 */
	public void setParentPosition(final PositionEntity parentPosition) {
		this.parentPosition = parentPosition;
	}

	/**
	 *
	 * @return the positions that report to this position
	 */
	public Set<PositionEntity> getSubPositions() {
		return subPositions;
	}

	/**
	 * Add a report position.
	 *
	 * @param position the position to add
	 */
	public void addSubPosition(final PositionEntity position) {
		if (subPositions == null) {
			subPositions = new HashSet<>();
		}
		subPositions.add(position);
		position.setParentPosition(this);
	}

	/**
	 * Remove a report position.
	 *
	 * @param position the position to remove
	 */
	public void removeSubPosition(final PositionEntity position) {
		if (subPositions != null) {
			subPositions.remove(position);
		}
		position.setParentPosition(null);
	}

	/**
	 *
	 * @return the contacts in this position
	 */
	public Set<ContactEntity> getContacts() {
		return contacts;
	}

	/**
	 * Add a contact.
	 *
	 * @param contact the contact to add
	 */
	public void addContact(final ContactEntity contact) {
		if (contacts == null) {
			contacts = new HashSet<>();
		}
		contacts.add(contact);
		contact.addPosition(this);
	}

	/**
	 * Remove a contact.
	 *
	 * @param contact the contact to remove
	 */
	public void removeContact(final ContactEntity contact) {
		if (contacts != null) {
			contacts.remove(contact);
		}
		contact.removePosition(this);
	}

	/**
	 *
	 * @return the org units this position manages
	 */
	public Set<OrgUnitEntity> getManageOrgUnits() {
		return manageOrgUnits;
	}

	/**
	 * Add an org unit for this position to manage.
	 *
	 * @param orgUnit the org unit to add
	 */
	public void addManageOrgUnit(final OrgUnitEntity orgUnit) {
		if (manageOrgUnits == null) {
			manageOrgUnits = new HashSet<>();
		}
		manageOrgUnits.add(orgUnit);
		orgUnit.setManagerPosition(this);
	}

	/**
	 * Remove an org unit.
	 *
	 * @param orgUnit the org unit to remove
	 */
	public void removeManageOrgUnit(final OrgUnitEntity orgUnit) {
		if (manageOrgUnits != null) {
			manageOrgUnits.remove(orgUnit);
		}
		orgUnit.setManagerPosition(null);
	}

	/**
	 *
	 * @return the org unit this position belongs to
	 */
	public OrgUnitEntity getOrgUnit() {
		return orgUnit;
	}

	/**
	 *
	 * @param orgUnit the org unit this position belongs to
	 */
	public void setOrgUnit(final OrgUnitEntity orgUnit) {
		this.orgUnit = orgUnit;
	}

}
