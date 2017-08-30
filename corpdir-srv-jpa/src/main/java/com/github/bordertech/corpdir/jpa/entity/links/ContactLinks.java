package com.github.bordertech.corpdir.jpa.entity.links;

import com.github.bordertech.corpdir.jpa.common.AbstractVersionable;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 * Contact version data.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactLinks extends AbstractVersionable<ContactEntity> {

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<PositionEntity> positions;

	public ContactLinks() {
	}

	public ContactLinks(final Integer versionId, final ContactEntity contact) {
		super(versionId, contact);
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
		position.getDataVersion(getVersionId()).addContact(getItem());
	}

	/**
	 * @param position the position to remove
	 */
	public void removePosition(final PositionEntity position) {
		if (positions != null) {
			positions.remove(position);
		}
		position.getDataVersion(getVersionId()).removeContact(getItem());
	}
}
