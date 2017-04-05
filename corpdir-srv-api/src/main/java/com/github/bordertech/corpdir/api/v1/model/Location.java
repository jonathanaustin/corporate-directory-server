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

	private Address address;
	private String parentId;
	private List<String> subIds;

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
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parent location id
	 */
	public void setParentId(final String parentId) {
		this.parentId = parentId;
	}

	/**
	 *
	 * @return the list of sub location keys
	 */
	public List<String> getSubIds() {
		if (subIds == null) {
			subIds = new ArrayList<>();
		}
		return subIds;
	}

	/**
	 *
	 * @param subIds the list of sub locations
	 */
	public void setSubIds(final List<String> subIds) {
		this.subIds = subIds;
	}

}
