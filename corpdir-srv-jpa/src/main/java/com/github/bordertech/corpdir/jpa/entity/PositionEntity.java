package com.github.bordertech.corpdir.jpa.entity;

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
public class PositionEntity extends AbstractPersistentObject {

	@ManyToOne
	private PositionTypeEntity type;

	@ManyToOne
	@JoinColumn(name = "reportToPosition_Id")
	private PositionEntity reportToPosition;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reportToPosition")
	private Set<PositionEntity> reportPositions;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<OrgUnitEntity> manageOrgUnits;

	@ManyToOne
	private OrgUnitEntity belongsToOrgUnit;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<ContactEntity> contacts;

	private String description;

	/**
	 * Default constructor.
	 */
	protected PositionEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 * @param businessKey the business key.
	 */
	public PositionEntity(final Long id, final String businessKey) {
		super(id, businessKey);
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
	 * @return the position this position reports to
	 */
	public PositionEntity getReportToPosition() {
		return reportToPosition;
	}

	/**
	 * @param reportToPosition the position this position reports to
	 */
	public void setReportToPosition(final PositionEntity reportToPosition) {
		this.reportToPosition = reportToPosition;
	}

	/**
	 *
	 * @return the positions that report to this position
	 */
	public Set<PositionEntity> getReportPositions() {
		return reportPositions;
	}

	/**
	 * Add a report position.
	 *
	 * @param position the position to add
	 */
	public void addReportPosition(final PositionEntity position) {
		if (reportPositions == null) {
			reportPositions = new HashSet<>();
		}
		reportPositions.add(position);
		position.setReportToPosition(this);
	}

	/**
	 * Remove a report position.
	 *
	 * @param position the position to remove
	 */
	public void removeReportPosition(final PositionEntity position) {
		if (reportPositions != null) {
			reportPositions.remove(position);
		}
		position.setReportToPosition(null);
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
	public OrgUnitEntity getBelongsToOrgUnit() {
		return belongsToOrgUnit;
	}

	/**
	 *
	 * @param belongsToOrgUnit the org unit this position belongs to
	 */
	public void setBelongsToOrgUnit(final OrgUnitEntity belongsToOrgUnit) {
		this.belongsToOrgUnit = belongsToOrgUnit;
	}

}
