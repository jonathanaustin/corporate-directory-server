package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiKeyIdObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnit extends AbstractApiKeyIdObject {

	private String description;
	private String typeKey;
	private String managerPosKey;
	private String parentKey;
	private List<String> subKeys;
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
	public String getManagerPosKey() {
		return managerPosKey;
	}

	/**
	 *
	 * @param managerPosKey the manager position key
	 */
	public void setManagerPosKey(final String managerPosKey) {
		this.managerPosKey = managerPosKey;
	}

	/**
	 *
	 * @return the parent org unit key or null if no parent
	 */
	public String getParentKey() {
		return parentKey;
	}

	/**
	 *
	 * @param parentKey the parent org unit key
	 */
	public void setParentKey(final String parentKey) {
		this.parentKey = parentKey;
	}

	/**
	 *
	 * @return the list of sub org unit keys
	 */
	public List<String> getSubKeys() {
		if (subKeys == null) {
			subKeys = new ArrayList<>();
		}
		return subKeys;
	}

	/**
	 *
	 * @param subKeys the list of sub org unit keys
	 */
	public void setSubKeys(final List<String> subKeys) {
		this.subKeys = subKeys;
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
