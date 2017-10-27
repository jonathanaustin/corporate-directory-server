package com.github.bordertech.corpdir.jpa.entity.links;

import com.github.bordertech.corpdir.jpa.common.DefaultVersionableTreeObject;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class PositionLinksEntity extends DefaultVersionableTreeObject<PositionLinksEntity, PositionEntity> {

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<OrgUnitEntity> manageOrgUnits;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private OrgUnitEntity orgUnit;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<ContactEntity> contacts;

	public PositionLinksEntity() {
	}

	public PositionLinksEntity(final VersionCtrlEntity versionCtrl, final PositionEntity position) {
		super(versionCtrl, position);
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
		contact.getOrCreateDataVersion(getVersionCtrl()).addPosition(getItem());
		// Bi-Directional - Add the Position to the Contact
		ContactLinksEntity conLinks = contact.getOrCreateDataVersion(getVersionCtrl());
		// To stop a circular call only add if not already there
		if (!conLinks.getPositions().contains(getItem())) {
			conLinks.addPosition(getItem());
		}
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
		// Bi-Directional - Remove the Position from the Contact
		ContactLinksEntity conLinks = contact.getOrCreateDataVersion(getVersionCtrl());
		// To stop a circular call only remove if already there
		if (conLinks.getPositions().contains(getItem())) {
			conLinks.removePosition(getItem());
		}
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
		orgUnit.getOrCreateDataVersion(getVersionCtrl()).setManagerPosition(getItem());
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
		orgUnit.getOrCreateDataVersion(getVersionCtrl()).setManagerPosition(null);
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
