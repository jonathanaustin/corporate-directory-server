package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiKeyIdObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Location of contact.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Location extends AbstractApiKeyIdObject {

	private String description;
	private Address address;
	private String parentKey;
	private List<String> subKeys;

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
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 *
	 * @param address the address
	 */
	public void setAddress(final Address address) {
		this.address = address;
	}

	/**
	 *
	 * @return the parent location key or null if no parent
	 */
	public String getParentKey() {
		return parentKey;
	}

	/**
	 * @param parentKey the parent location id
	 */
	public void setParentKey(final String parentKey) {
		this.parentKey = parentKey;
	}

	/**
	 *
	 * @return the list of sub location keys
	 */
	public List<String> getSubKeys() {
		if (subKeys == null) {
			subKeys = new ArrayList<>();
		}
		return subKeys;
	}

	/**
	 *
	 * @param subKeys the list of sub locations
	 */
	public void setSubKeys(final List<String> subKeys) {
		this.subKeys = subKeys;
	}

}
