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

	private String typeId;
	private String managerPosId;
	private String parentId;
	private List<String> subIds;
	private List<String> positionIds;

	/**
	 *
	 * @return the organization type key
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 *
	 * @param typeId the organization type key
	 */
	public void setTypeId(final String typeId) {
		this.typeId = typeId;
	}

	/**
	 *
	 * @return the manager position key
	 */
	public String getManagerPosId() {
		return managerPosId;
	}

	/**
	 *
	 * @param managerPosId the manager position key
	 */
	public void setManagerPosId(final String managerPosId) {
		this.managerPosId = managerPosId;
	}

	/**
	 *
	 * @return the parent org unit key or null if no parent
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 *
	 * @param parentId the parent org unit key
	 */
	public void setParentId(final String parentId) {
		this.parentId = parentId;
	}

	/**
	 *
	 * @return the list of sub org unit keys
	 */
	public List<String> getSubIds() {
		if (subIds == null) {
			subIds = new ArrayList<>();
		}
		return subIds;
	}

	/**
	 *
	 * @param subIds the list of sub org unit keys
	 */
	public void setSubIds(final List<String> subIds) {
		this.subIds = subIds;
	}

	/**
	 *
	 * @return the position keys that belong to this org unit
	 */
	public List<String> getPositionIds() {
		if (positionIds == null) {
			positionIds = new ArrayList<>();
		}
		return positionIds;
	}

	/**
	 *
	 * @param positionIds the list of position keys that belong to this org unit
	 */
	public void setPositionIds(final List<String> positionIds) {
		this.positionIds = positionIds;
	}

}
