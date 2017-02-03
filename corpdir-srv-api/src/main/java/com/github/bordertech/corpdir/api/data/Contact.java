package com.github.bordertech.corpdir.api.data;

import java.io.Serializable;
import java.util.List;

/**
 * Contact details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Contact implements Serializable {

	private Long id;
	private String alternateKey;
	private String firstName;
	private String lastName;
	private String companyTitle;
	private String address;
	private Location location;
	private List<Channel> channels;
	private Long imageId;
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
	 * @return the alternate key
	 */
	public String getAlternateKey() {
		return alternateKey;
	}

	/**
	 *
	 * @param alternateKey the alternate key
	 */
	public void setAlternateKey(final String alternateKey) {
		this.alternateKey = alternateKey;
	}

	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the first name
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the last name
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the company title
	 */
	public String getCompanyTitle() {
		return companyTitle;
	}

	/**
	 * @param companyTitle the company title
	 */
	public void setCompanyTitle(final String companyTitle) {
		this.companyTitle = companyTitle;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 *
	 * @param address the address
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location
	 */
	public void setLocation(final Location location) {
		this.location = location;
	}

	/**
	 *
	 * @return the channels
	 */
	public List<Channel> getChannels() {
		return channels;
	}

	/**
	 *
	 * @param channels the channels
	 */
	public void setChannels(final List<Channel> channels) {
		this.channels = channels;
	}

	/**
	 *
	 * @return the image id
	 */
	public Long getImageId() {
		return imageId;
	}

	/**
	 *
	 * @param imageId the image id
	 */
	public void setImageId(final Long imageId) {
		this.imageId = imageId;
	}

	/**
	 *
	 * @return true if active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active
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
