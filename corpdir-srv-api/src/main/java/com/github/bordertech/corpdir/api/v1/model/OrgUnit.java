package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiObject;
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
	private String typeKey;
	private String managerPositionKey;
	private String parentOrgUnitKey;
	private List<String> subOrgUnitKeys;
	private List<String> positionKeys;

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
	 * @return the organization type key
	 */
	public String getTypeKey() {
		return typeKey;
	}

	/**
	 *
	 * @param typeKey the organization type key
	 */
	public void setTypeKey(final String typeKey) {
		this.typeKey = typeKey;
	}

	/**
	 *
	 * @return the manager position key
	 */
	public String getManagerPositionKey() {
		return managerPositionKey;
	}

	/**
	 *
	 * @param managerPositionKey the manager position key
	 */
	public void setManagerPositionKey(final String managerPositionKey) {
		this.managerPositionKey = managerPositionKey;
	}

	/**
	 *
	 * @return the parent org unit key or null if no parent
	 */
	public String getParentOrgUnitKey() {
		return parentOrgUnitKey;
	}

	/**
	 *
	 * @param parentOrgUnitKey the parent org unit key
	 */
	public void setParentOrgUnitKey(final String parentOrgUnitKey) {
		this.parentOrgUnitKey = parentOrgUnitKey;
	}

	/**
	 *
	 * @return the list of sub org unit keys
	 */
	public List<String> getSubOrgUnitKeys() {
		if (subOrgUnitKeys == null) {
			subOrgUnitKeys = new ArrayList<>();
		}
		return subOrgUnitKeys;
	}

	/**
	 *
	 * @param subOrgUnitKeys the list of sub org unit keys
	 */
	public void setSubOrgUnitKeys(final List<String> subOrgUnitKeys) {
		this.subOrgUnitKeys = subOrgUnitKeys;
	}

	/**
	 *
	 * @return the position keys that belong to this org unit
	 */
	public List<String> getPositionKeys() {
		if (positionKeys == null) {
			positionKeys = new ArrayList<>();
		}
		return positionKeys;
	}

	/**
	 *
	 * @param positionKeys the list of position keys that belong to this org unit
	 */
	public void setPositionKeys(final List<String> positionKeys) {
		this.positionKeys = positionKeys;
	}

}
