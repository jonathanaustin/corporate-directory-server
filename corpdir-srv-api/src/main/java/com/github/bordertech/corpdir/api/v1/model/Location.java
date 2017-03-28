package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Location of contact.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Location extends AbstractApiObject {

	private String description;
	private Address address;
	private String parentLocationKey;
	private List<String> subLocationKeys;

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
	public String getParentLocationKey() {
		return parentLocationKey;
	}

	/**
	 * @param parentLocationKey the parent location id
	 */
	public void setParentLocationKey(final String parentLocationKey) {
		this.parentLocationKey = parentLocationKey;
	}

	/**
	 *
	 * @return the list of sub location keys
	 */
	public List<String> getSubLocationKeys() {
		if (subLocationKeys == null) {
			subLocationKeys = new ArrayList<>();
		}
		return subLocationKeys;
	}

	/**
	 *
	 * @param subLocationKeys the list of sub locations
	 */
	public void setSubLocationKeys(final List<String> subLocationKeys) {
		this.subLocationKeys = subLocationKeys;
	}

}
