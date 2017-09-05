package com.github.bordertech.corpdir.jpa.entity.links;

import com.github.bordertech.corpdir.jpa.common.DefaultVersionableObject;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Contact version data.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "ContactLinks")
public class ContactLinksEntity extends DefaultVersionableObject<ContactLinksEntity, ContactEntity> {

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<PositionEntity> positions;

	public ContactLinksEntity() {
	}

	public ContactLinksEntity(final VersionCtrlEntity versionCtrl, final ContactEntity contact) {
		super(versionCtrl, contact);
	}

	/**
	 *
	 * @return the positions
	 */
	public Set<PositionEntity> getPositions() {
		return positions;
	}

	/**
	 * @param position the position to add
	 */
	public void addPosition(final PositionEntity position) {
		if (positions == null) {
			positions = new HashSet<>();
		}
		positions.add(position);
		// Bi-Directional
		position.getOrCreateDataVersion(getVersionCtrl()).addContact(getItem());
	}

	/**
	 * @param position the position to remove
	 */
	public void removePosition(final PositionEntity position) {
		if (positions != null) {
			positions.remove(position);
		}
		// Bi-Directional
		position.getOrCreateDataVersion(getVersionCtrl()).removeContact(getItem());
	}
}
