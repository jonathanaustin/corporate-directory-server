package com.github.bordertech.corpdir.jpa.entity.version;

import com.github.bordertech.corpdir.jpa.common.version.DefaultItemTreeVersion;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Position links that can be versioned.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "PositionLinks")
public class PositionVersionEntity extends DefaultItemTreeVersion<PositionEntity, PositionVersionEntity> {

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<OrgUnitVersionEntity> manageOrgUnits;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private OrgUnitVersionEntity orgUnit;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<ContactVersionEntity> contacts;

	public PositionVersionEntity() {
	}

	public PositionVersionEntity(final VersionCtrlEntity versionCtrl, final PositionEntity position) {
		super(versionCtrl, position);
	}

	/**
	 *
	 * @return the contacts in this position
	 */
	public Set<ContactEntity> getContacts() {
		return extractItems(contacts);
	}

	/**
	 * Add a contact.
	 *
	 * @param contact the contact to add
	 */
	public void addContact(final ContactEntity contact) {
		ContactVersionEntity vers = contact.getOrCreateVersion(getVersionCtrl());
		if (contacts == null) {
			contacts.add(vers);
		}
		// Bi-Directional - Add the Position to the Contact
		// To stop a circular call only add if not already there
		if (!vers.getPositions().contains(getItem())) {
			vers.addPosition(getItem());
		}
	}

	/**
	 * Remove a contact.
	 *
	 * @param contact the contact to remove
	 */
	public void removeContact(final ContactEntity contact) {
		ContactVersionEntity vers = contact.getOrCreateVersion(getVersionCtrl());
		if (contacts != null) {
			contacts.remove(vers);
		}
		// Bi-Directional - Remove the Position from the Contact
		// To stop a circular call only remove if already there
		if (vers.getPositions().contains(getItem())) {
			vers.removePosition(getItem());
		}
	}

	/**
	 *
	 * @return the org units this position manages
	 */
	public Set<OrgUnitEntity> getManageOrgUnits() {
		return extractItems(manageOrgUnits);
	}

	/**
	 * Add an org unit for this position to manage.
	 *
	 * @param orgUnit the org unit to add
	 */
	public void addManageOrgUnit(final OrgUnitEntity orgUnit) {
		OrgUnitVersionEntity vers = orgUnit.getOrCreateVersion(getVersionCtrl());
		if (manageOrgUnits == null) {
			manageOrgUnits = new HashSet<>();
		}
		manageOrgUnits.add(vers);
		vers.setManagerPosition(getItem());
	}

	/**
	 * Remove an org unit.
	 *
	 * @param orgUnit the org unit to remove
	 */
	public void removeManageOrgUnit(final OrgUnitEntity orgUnit) {
		OrgUnitVersionEntity vers = orgUnit.getOrCreateVersion(getVersionCtrl());
		if (manageOrgUnits != null) {
			manageOrgUnits.remove(vers);
		}
		vers.setManagerPosition(null);
	}

	/**
	 *
	 * @return the org unit this position belongs to
	 */
	public OrgUnitEntity getOrgUnit() {
		return orgUnit == null ? null : orgUnit.getItem();
	}

	/**
	 *
	 * @param orgUnit the org unit this position belongs to
	 */
	public void setOrgUnit(final OrgUnitEntity orgUnit) {
		if (orgUnit == null) {
			this.orgUnit = null;
			return;
		}
		this.orgUnit = orgUnit.getOrCreateVersion(getVersionCtrl());
	}
}
