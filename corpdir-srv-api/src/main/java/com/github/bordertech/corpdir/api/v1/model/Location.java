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
	private String parentLocationId;
	private List<String> subLocationIds;

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
	 * @return the parent location id or null if no parent
	 */
	public String getParentLocationId() {
		return parentLocationId;
	}

	/**
	 * @param parentLocationId the parent location id
	 */
	public void setParentLocationId(final String parentLocationId) {
		this.parentLocationId = parentLocationId;
	}

	/**
	 *
	 * @return the list of sub location ids
	 */
	public List<String> getSubLocationIds() {
		if (subLocationIds == null) {
			subLocationIds = new ArrayList<>();
		}
		return subLocationIds;
	}

	/**
	 *
	 * @param subLocationIds the list of sub locations
	 */
	public void setSubLocationIds(final List<String> subLocationIds) {
		this.subLocationIds = subLocationIds;
	}

}
