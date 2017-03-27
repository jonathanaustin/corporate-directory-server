package com.github.bordertech.corpdir.api.v1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnit extends AbstractApiObject {

	private String description;
	private UnitType type;
	private String managerPositionId;
	private String parentOrgUnitId;
	private List<String> subOrgUnitIds;
	private List<String> positionIds;

	/**
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @param description the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 *
	 * @return the organization type
	 */
	public UnitType getType() {
		return type;
	}

	/**
	 *
	 * @param type the organization type
	 */
	public void setType(final UnitType type) {
		this.type = type;
	}

	/**
	 *
	 * @return the manager position id
	 */
	public String getManagerPositionId() {
		return managerPositionId;
	}

	/**
	 *
	 * @param managerPositionId the manager position id
	 */
	public void setManagerPositionId(final String managerPositionId) {
		this.managerPositionId = managerPositionId;
	}

	/**
	 *
	 * @return the parent org unit id or null if no parent
	 */
	public String getParentOrgUnitId() {
		return parentOrgUnitId;
	}

	/**
	 *
	 * @param parentOrgUnitId the parent org unit id
	 */
	public void setParentOrgUnitId(final String parentOrgUnitId) {
		this.parentOrgUnitId = parentOrgUnitId;
	}

	/**
	 *
	 * @return the list of sub org unit ids
	 */
	public List<String> getSubOrgUnitIds() {
		if (subOrgUnitIds == null) {
			subOrgUnitIds = new ArrayList<>();
		}
		return subOrgUnitIds;
	}

	/**
	 *
	 * @param subOrgUnitIds the list of sub org unit ids
	 */
	public void setSubOrgUnitIds(final List<String> subOrgUnitIds) {
		this.subOrgUnitIds = subOrgUnitIds;
	}

	/**
	 *
	 * @return the position ids that belong to this org unit
	 */
	public List<String> getPositionIds() {
		if (positionIds == null) {
			positionIds = new ArrayList<>();
		}
		return positionIds;
	}

	/**
	 *
	 * @param positionIds the list of position ids that belong to this org unit
	 */
	public void setPositionIds(final List<String> positionIds) {
		this.positionIds = positionIds;
	}

}
