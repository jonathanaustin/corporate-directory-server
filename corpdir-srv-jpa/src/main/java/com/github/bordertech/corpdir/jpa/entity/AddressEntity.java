package com.github.bordertech.corpdir.jpa.entity;

import javax.persistence.Embeddable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistObject;

/**
 * Address details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Embeddable
public class AddressEntity implements PersistObject {

	private String workStation;
	private String line1;
	private String line2;
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
	public String getLine1() {
		return line1;
	}

	/**
	 *
	 * @param line1 address line 1
	 */
	public void setLine1(final String line1) {
		this.line1 = line1;
	}

	/**
	 *
	 * @return address line 2
	 */
	public String getLine2() {
		return line2;
	}

	/**
	 *
	 * @param line2 address line 2
	 */
	public void setLine2(final String line2) {
		this.line2 = line2;
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
