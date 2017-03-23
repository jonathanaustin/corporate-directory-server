package com.github.bordertech.corpdir.jpa.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Address details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Embeddable
public class AddressEntity implements Serializable {

	private String workStation;
	private String addressLine1;
	private String addressLine2;
	private String suburb;
	private String state;
	private String postcode;
	private String country;

	/**
	 *
	 * @return the work station
	 */
	public String getWorkStation() {
		return workStation;
	}

	/**
	 *
	 * @param workStation the work station
	 */
	public void setWorkStation(final String workStation) {
		this.workStation = workStation;
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

}
