package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.DefaultVersionTreeObject;
import com.github.bordertech.corpdir.api.v1.model.links.OrgUnitLinks;
import java.util.ArrayList;
import java.util.List;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnit extends DefaultVersionTreeObject implements OrgUnitLinks {

	private String typeId;
	private String managerPosId;
	private List<String> positionIds;

	protected OrgUnit() {
	}

	public OrgUnit(final String id) {
		super(id);
	}

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
	@Override
	public String getManagerPosId() {
		return managerPosId;
	}

	/**
	 *
	 * @param managerPosId the manager position key
	 */
	@Override
	public void setManagerPosId(final String managerPosId) {
		this.managerPosId = managerPosId;
	}

	/**
	 *
	 * @return the position keys that belong to this org unit
	 */
	@Override
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
	@Override
	public void setPositionIds(final List<String> positionIds) {
		this.positionIds = positionIds;
	}

}
