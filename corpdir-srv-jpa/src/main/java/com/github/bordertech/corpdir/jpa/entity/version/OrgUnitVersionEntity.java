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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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

	/**
	 * Position assigned as the manager.
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumns({
		@JoinColumn(referencedColumnName = "item_id", name = "managerPosition_item_id")
		, @JoinColumn(referencedColumnName = "versionCtrl_id", name = "managerPosition_versionCtrl_id")
	})
	private PositionVersionEntity managerPositionVersion;

	/**
	 * Positions owned by this org unit.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orgUnitVersion", cascade = CascadeType.MERGE)
	private Set<PositionVersionEntity> positionVersions;

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
		PositionVersionEntity vers = getManagerPositionVersion();
		return vers == null ? null : vers.getItem();
	}

	/**
	 *
	 * @param mgrPosition the manager position for this org unit
	 */
	public void setManagerPosition(final PositionEntity mgrPosition) {
		if (mgrPosition == null) {
			managerPositionVersion = null;
			return;
		}
		PositionVersionEntity vers = mgrPosition.getOrCreateVersion(getVersionCtrl());
		managerPositionVersion = vers;
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
		return extractItems(getPositionVersions());
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
		PositionVersionEntity vers = position.getOrCreateVersion(getVersionCtrl());
		getPositionVersions().add(vers);
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
		getPositionVersions().remove(vers);
		// Bi-Directional
		vers.setOrgUnit(null);
	}

	public PositionVersionEntity getManagerPositionVersion() {
		return managerPositionVersion;
	}

	public Set<PositionVersionEntity> getPositionVersions() {
		if (positionVersions == null) {
			positionVersions = new HashSet<>();
		}
		return positionVersions;
	}

}
