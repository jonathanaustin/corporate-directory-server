package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiKeyIdObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Position in organization.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Position extends AbstractApiKeyIdObject {

	private String typeId;
	private String ouId;
	private String parentId;
	private List<String> subIds;
	private List<String> manageOuIds;
	private List<String> contactIds;

	/**
	 *
	 * @return the position type key
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 *
	 * @param typeId the position type key
	 */
	public void setTypeId(final String typeId) {
		this.typeId = typeId;
	}

	/**
	 *
	 * @return the org unit key the position belongs to
	 */
	public String getOuId() {
		return ouId;
	}

	/**
	 *
	 * @param ouId the org unit key the position belongs to
	 */
	public void setOuId(final String ouId) {
		this.ouId = ouId;
	}

	/**
	 * @return the position key this position reports to
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the position key this position reports to
	 */
	public void setParentId(final String parentId) {
		this.parentId = parentId;
	}

	/**
	 *
	 * @return the position key that report to this position
	 */
	public List<String> getSubIds() {
		if (subIds == null) {
			subIds = new ArrayList<>();
		}
		return subIds;
	}

	/**
	 * @param subIds the position keys that report to this position
	 */
	public void setSubIds(final List<String> subIds) {
		this.subIds = subIds;
	}

	/**
	 *
	 * @return the org unit keys this position manages
	 */
	public List<String> getManageOuIds() {
		if (manageOuIds == null) {
			manageOuIds = new ArrayList<>();
		}
		return manageOuIds;
	}

	/**
	 *
	 * @param manageOuIds the org unit keys this position manages
	 */
	public void setManageOuIds(final List<String> manageOuIds) {
		this.manageOuIds = manageOuIds;
	}

	/**
	 *
	 * @return the contact keys for this position
	 */
	public List<String> getContactIds() {
		if (contactIds == null) {
			contactIds = new ArrayList<>();
		}
		return contactIds;
	}

	/**
	 *
	 * @param contactIds the contact keys for this position
	 */
	public void setContactIds(final List<String> contactIds) {
		this.contactIds = contactIds;
	}

}
