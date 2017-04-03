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

	private String description;
	private String typeKey;
	private String ouKey;
	private String parentKey;
	private List<String> subKeys;
	private List<String> manageOuKeys;
	private List<String> contactKeys;

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
	 * @return the position type key
	 */
	public String getTypeKey() {
		return typeKey;
	}

	/**
	 *
	 * @param typeKey the position type key
	 */
	public void setTypeKey(final String typeKey) {
		this.typeKey = typeKey;
	}

	/**
	 *
	 * @return the org unit key the position belongs to
	 */
	public String getOuKey() {
		return ouKey;
	}

	/**
	 *
	 * @param ouKey the org unit key the position belongs to
	 */
	public void setOuKey(final String ouKey) {
		this.ouKey = ouKey;
	}

	/**
	 * @return the position key this position reports to
	 */
	public String getParentKey() {
		return parentKey;
	}

	/**
	 * @param parentKey the position key this position reports to
	 */
	public void setParentKey(final String parentKey) {
		this.parentKey = parentKey;
	}

	/**
	 *
	 * @return the position key that report to this position
	 */
	public List<String> getSubKeys() {
		if (subKeys == null) {
			subKeys = new ArrayList<>();
		}
		return subKeys;
	}

	/**
	 * @param subKeys the position keys that report to this position
	 */
	public void setSubKeys(final List<String> subKeys) {
		this.subKeys = subKeys;
	}

	/**
	 *
	 * @return the org unit keys this position manages
	 */
	public List<String> getManageOuKeys() {
		if (manageOuKeys == null) {
			manageOuKeys = new ArrayList<>();
		}
		return manageOuKeys;
	}

	/**
	 *
	 * @param manageOuKeys the org unit keys this position manages
	 */
	public void setManageOuKeys(final List<String> manageOuKeys) {
		this.manageOuKeys = manageOuKeys;
	}

	/**
	 *
	 * @return the contact keys for this position
	 */
	public List<String> getContactKeys() {
		if (contactKeys == null) {
			contactKeys = new ArrayList<>();
		}
		return contactKeys;
	}

	/**
	 *
	 * @param contactKeys the contact keys for this position
	 */
	public void setContactKeys(final List<String> contactKeys) {
		this.contactKeys = contactKeys;
	}

}
