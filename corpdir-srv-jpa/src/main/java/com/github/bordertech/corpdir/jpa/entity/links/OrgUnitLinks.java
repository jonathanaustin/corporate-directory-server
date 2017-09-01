package com.github.bordertech.corpdir.jpa.entity.links;

import com.github.bordertech.corpdir.jpa.common.DefaultVersionableTreeObject;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Organization unit links that can be versioned.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "OrgUnitLinks")
public class OrgUnitLinks extends DefaultVersionableTreeObject<OrgUnitLinks, OrgUnitEntity> {

	@ManyToOne(fetch = FetchType.LAZY)
	private PositionEntity managerPosition;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<PositionEntity> positions;

	public OrgUnitLinks() {
	}

	public OrgUnitLinks(final Integer versionId, final OrgUnitEntity orgUnit) {
		super(versionId, orgUnit);
	}

	/**
	 *
	 * @return the manager position for this org unit
	 */
	public PositionEntity getManagerPosition() {
		return managerPosition;
	}

	/**
	 *
	 * @param managerPosition the manager position for this org unit
	 */
	public void setManagerPosition(final PositionEntity managerPosition) {
		this.managerPosition = managerPosition;
	}

	/**
	 *
	 * @return the positions belonging to this unit
	 */
	public Set<PositionEntity> getPositions() {
		return positions;
	}

	/**
	 * Add a position.
	 *
	 * @param position the position to add
	 */
	public void addPosition(final PositionEntity position) {
		if (positions == null) {
			positions = new HashSet<>();
		}
		positions.add(position);
		position.getDataVersion(getVersionId()).setOrgUnit(getItem());
	}

	/**
	 * Remove a position.
	 *
	 *
	 * @param position the position to remove
	 */
	public void removePosition(final PositionEntity position) {
		if (positions != null) {
			positions.remove(position);
		}
		position.getDataVersion(getVersionId()).setOrgUnit(null);
	}

}
