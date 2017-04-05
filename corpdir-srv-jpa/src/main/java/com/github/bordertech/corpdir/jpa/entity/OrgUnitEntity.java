package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.AbstractPersistentKeyIdObject;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "OrgUnit")
public class OrgUnitEntity extends AbstractPersistentKeyIdObject {

	@ManyToOne
	private UnitTypeEntity type;

	@ManyToOne
	@JoinColumn(name = "parentOrgUnit_Id")
	private OrgUnitEntity parentOrgUnit;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentOrgUnit")
	private Set<OrgUnitEntity> subOrgUnits;

	@ManyToOne(fetch = FetchType.LAZY)
	private PositionEntity managerPosition;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<PositionEntity> positions;

	/**
	 * Default constructor.
	 */
	protected OrgUnitEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public OrgUnitEntity(final Long id) {
		super(id);
	}

	/**
	 *
	 * @return the organization type
	 */
	public UnitTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the organization type
	 */
	public void setType(final UnitTypeEntity type) {
		this.type = type;
	}

	/**
	 * @return the parent org unit
	 */
	public OrgUnitEntity getParentOrgUnit() {
		return parentOrgUnit;
	}

	/**
	 * @param parentOrgUnit the parent org unit
	 */
	public void setParentOrgUnit(final OrgUnitEntity parentOrgUnit) {
		this.parentOrgUnit = parentOrgUnit;
	}

	/**
	 *
	 * @return the units managed by this unit
	 */
	public Set<OrgUnitEntity> getSubOrgUnits() {
		return subOrgUnits;
	}

	/**
	 * Add a sub org unit.
	 *
	 * @param orgUnit the sub org unit to add
	 */
	public void addSubOrgUnit(final OrgUnitEntity orgUnit) {
		if (subOrgUnits == null) {
			subOrgUnits = new HashSet<>();
		}
		subOrgUnits.add(orgUnit);
		orgUnit.setParentOrgUnit(this);
	}

	/**
	 * Remove a sub org unit.
	 *
	 * @param orgUnit the orgUnit to remove
	 */
	public void removeSubOrgUnit(final OrgUnitEntity orgUnit) {
		if (subOrgUnits != null) {
			subOrgUnits.remove(orgUnit);
		}
		orgUnit.setParentOrgUnit(null);
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
		position.setOrgUnit(this);
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
		position.setOrgUnit(null);
	}

}
