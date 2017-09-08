package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.DefaultTreeObject;

/**
 * Location of contact.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Location extends DefaultTreeObject {

	private Address address;

	protected Location() {
	}

	public Location(final String id) {
		super(id);
	}

	/**
	 *
	 * @return the address
	 */
	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
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
