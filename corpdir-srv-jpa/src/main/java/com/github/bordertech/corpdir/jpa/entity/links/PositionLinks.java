package com.github.bordertech.corpdir.jpa.entity.links;

import com.github.bordertech.corpdir.jpa.common.DefaultVersionableTreeObject;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import java.util.HashSet;
import java.util.Set;
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
public class PositionLinks extends DefaultVersionableTreeObject<PositionLinks, PositionEntity> {

	@OneToMany(fetch = FetchType.LAZY)
	private Set<OrgUnitEntity> manageOrgUnits;

	@ManyToOne
	private OrgUnitEntity orgUnit;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<ContactEntity> contacts;

	public PositionLinks() {
	}

	public PositionLinks(final Integer versionId, final PositionEntity position) {
		super(versionId, position);
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
		contact.getDataVersion(getVersionId()).addPosition(getItem());
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
		contact.getDataVersion(getVersionId()).removePosition(getItem());
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
		orgUnit.getDataVersion(getVersionId()).setManagerPosition(getItem());
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
		orgUnit.getDataVersion(getVersionId()).setManagerPosition(null);
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
