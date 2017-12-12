package com.github.bordertech.corpdir.jpa.entity.version;

import com.github.bordertech.corpdir.jpa.common.version.DefaultItemVersion;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Contact version data.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "ContactLinks")
public class ContactVersionEntity extends DefaultItemVersion<ContactEntity, ContactVersionEntity> {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Set<PositionVersionEntity> positions;

	public ContactVersionEntity() {
	}

	public ContactVersionEntity(final VersionCtrlEntity versionCtrl, final ContactEntity contact) {
		super(versionCtrl, contact);
	}

	/**
	 *
	 * @return the positions
	 */
	public Set<PositionEntity> getPositions() {
		return extractItems(positions);
	}

	/**
	 * @param position the position to add
	 */
	public void addPosition(final PositionEntity position) {
		if (position == null) {
			return;
		}
		// Add position
		if (positions == null) {
			positions = new HashSet<>();
		}
		PositionVersionEntity vers = position.getOrCreateVersion(getVersionCtrl());
		positions.add(vers);
		// Bi-Directional
		// To stop a circular call only add if not already there
		if (!vers.getContacts().contains(getItem())) {
			vers.addContact(getItem());
		}
	}

	/**
	 * @param position the position to remove
	 */
	public void removePosition(final PositionEntity position) {
		if (position == null) {
			return;
		}
		// Remove position
		PositionVersionEntity vers = position.getOrCreateVersion(getVersionCtrl());
		if (positions != null) {
			positions.remove(vers);
		}
		// Bi-Directional - Remove the Contact from the Position
		// To stop a circular call only remove if there
		if (vers.getContacts().contains(getItem())) {
			vers.removeContact(getItem());
		}
	}
}
