package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiTreeObject;

/**
 * Location of contact.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Location extends AbstractApiTreeObject {

	private Address address;

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

}
