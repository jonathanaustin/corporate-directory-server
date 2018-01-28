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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	/**
	 * OrgUnits managed by this Position.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "managerPositionVersion", cascade = CascadeType.MERGE)
	private Set<OrgUnitVersionEntity> manageOrgUnitVersions;

	/**
	 * Org Unit that own this position.
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumns({
		@JoinColumn(referencedColumnName = "item_id", name = "ownerOrgUnit_item_id")
		, @JoinColumn(referencedColumnName = "versionCtrl_id", name = "ownerOrgUnit_versionCtrl_id")
	})
	private OrgUnitVersionEntity orgUnitVersion;

	/**
	 * Contacts assigned to this position.
	 */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "positionVersions", cascade = CascadeType.MERGE)
	private Set<ContactVersionEntity> contactVersions;

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
		return extractItems(getContactVersions());
	}

	/**
	 * Add a contact.
	 *
	 * @param contact the contact to add
	 */
	public void addContact(final ContactEntity contact) {
		if (contact == null) {
			return;
		}
		// Add contact
		ContactVersionEntity vers = contact.getOrCreateVersion(getVersionCtrl());
		getContactVersions().add(vers);
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
		if (contact == null) {
			return;
		}
		// Remove contact
		ContactVersionEntity vers = contact.getOrCreateVersion(getVersionCtrl());
		getContactVersions().remove(vers);
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
		return extractItems(getManageOrgUnitVersions());
	}

	/**
	 * Add an org unit for this position to manage.
	 *
	 * @param orgUnit the org unit to add
	 */
	public void addManageOrgUnit(final OrgUnitEntity orgUnit) {
		if (orgUnit == null) {
			return;
		}
		// Add Managed Org Unit
		OrgUnitVersionEntity vers = orgUnit.getOrCreateVersion(getVersionCtrl());
		getManageOrgUnitVersions().add(vers);
		// Bi-Directional
		vers.setManagerPosition(getItem());
	}

	/**
	 * Remove an org unit.
	 *
	 * @param orgUnit the org unit to remove
	 */
	public void removeManageOrgUnit(final OrgUnitEntity orgUnit) {
		if (orgUnit == null) {
			return;
		}
		// Remove Managed Org Unit
		OrgUnitVersionEntity vers = orgUnit.getOrCreateVersion(getVersionCtrl());
		getManageOrgUnitVersions().remove(vers);
		// Bi-Directional
		vers.setManagerPosition(null);
	}

	/**
	 *
	 * @return the org unit this position belongs to
	 */
	public OrgUnitEntity getOrgUnit() {
		OrgUnitVersionEntity vers = getOrgUnitVersion();
		return vers == null ? null : vers.getItem();
	}

	/**
	 *
	 * @param orgUnit the org unit this position belongs to
	 */
	public void setOrgUnit(final OrgUnitEntity orgUnit) {
		if (orgUnit == null) {
			this.orgUnitVersion = null;
			return;
		}
		OrgUnitVersionEntity vers = orgUnit.getOrCreateVersion(getVersionCtrl());
		this.orgUnitVersion = vers;
		// Bi-Directional
		if (!vers.getPositions().contains(getItem())) {
			vers.addPosition(getItem());
		}
	}

	public Set<OrgUnitVersionEntity> getManageOrgUnitVersions() {
		if (manageOrgUnitVersions == null) {
			manageOrgUnitVersions = new HashSet<>();
		}
		return manageOrgUnitVersions;
	}

	public OrgUnitVersionEntity getOrgUnitVersion() {
		return orgUnitVersion;
	}

	public Set<ContactVersionEntity> getContactVersions() {
		if (contactVersions == null) {
			contactVersions = new HashSet<>();
		}
		return contactVersions;
	}

}
