package com.github.bordertech.corpdir.jpa.entity.version;

import com.github.bordertech.corpdir.jpa.common.version.DefaultItemTreeVersion;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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
public class OrgUnitVersionEntity extends DefaultItemTreeVersion<OrgUnitEntity, OrgUnitVersionEntity> {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private PositionVersionEntity managerPosition;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<PositionVersionEntity> positions;

	public OrgUnitVersionEntity() {
	}

	public OrgUnitVersionEntity(final VersionCtrlEntity versionCtrl, final OrgUnitEntity orgUnit) {
		super(versionCtrl, orgUnit);
	}

	/**
	 *
	 * @return the manager position for this org unit
	 */
	public PositionEntity getManagerPosition() {
		return managerPosition == null ? null : managerPosition.getItem();
	}

	/**
	 *
	 * @param managerPosition the manager position for this org unit
	 */
	public void setManagerPosition(final PositionEntity managerPosition) {
		if (managerPosition == null) {
			this.managerPosition = null;
			return;
		}
		PositionVersionEntity vers = managerPosition.getOrCreateVersion(getVersionCtrl());
		this.managerPosition = vers;
		// Bi-Directional
		if (!vers.getManageOrgUnits().contains(getItem())) {
			vers.addManageOrgUnit(getItem());
		}
	}

	/**
	 *
	 * @return the positions belonging to this unit
	 */
	public Set<PositionEntity> getPositions() {
		return extractItems(positions);
	}

	/**
	 * Assign a position to this ORG Unit.
	 *
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
		vers.setOrgUnit(getItem());
	}

	/**
	 * Remove a position that belongs to this ORG UNIT.
	 *
	 *
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
		// Bi-Directional
		vers.setOrgUnit(null);
	}

}
