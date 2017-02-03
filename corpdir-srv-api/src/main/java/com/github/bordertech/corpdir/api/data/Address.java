package com.github.bordertech.corpdir.api.data;

import java.io.Serializable;

/**
 * Address details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Address implements Serializable {

	private Long id;
	private String addressLine1;
	private String addressLine2;
	private String suburb;
	private String state;
	private String postcode;
	private String country;
	private boolean active;
	private boolean custom;

	/**
	 *
	 * @return the unique id
	 */
	public Long getId() {
		return id;
	}

	/**
	 *
	 * @param id the unique id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return address line 1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 *
	 * @param addressLine1 address line 1
	 */
	public void setAddressLine1(final String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 *
	 * @return address line 2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 *
	 * @param addressLine2 address line 2
	 */
	public void setAddressLine2(final String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 *
	 * @return the suburb
	 */
	public String getSuburb() {
		return suburb;
	}

	/**
	 *
	 * @param suburb the suburb
	 */
	public void setSuburb(final String suburb) {
		this.suburb = suburb;
	}

	/**
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 *
	 * @param state the state
	 */
	public void setState(final String state) {
		this.state = state;
	}

	/**
	 *
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 *
	 * @param postcode the postcode
	 */
	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	/**
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 *
	 * @param country the country
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 *
	 * @return true if active record
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active record
	 */
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @return true if custom record
	 */
	public boolean isCustom() {
		return custom;
	}

	/**
	 *
	 * @param custom true if custom record
	 */
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

}
