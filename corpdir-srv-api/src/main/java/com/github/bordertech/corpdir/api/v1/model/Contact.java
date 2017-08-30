package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.DefaultKeyIdObject;
import com.github.bordertech.corpdir.api.v1.model.links.ContactLinks;
import java.util.ArrayList;
import java.util.List;

/**
 * Contact details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Contact extends DefaultKeyIdObject implements ContactLinks {

	private String firstName;
	private String lastName;
	private String companyTitle;
	private Address address;
	private String locationId;
	private boolean hasImage;
	private List<Channel> channels;
	private List<String> positionIds;
	private Integer versionId;

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
	 * @return the location key
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the location key
	 */
	public void setLocationId(final String locationId) {
		this.locationId = locationId;
	}

	/**
	 *
	 * @return true if contact has image
	 */
	public boolean getHasImage() {
		return hasImage;
	}

	/**
	 *
	 * @param hasImage true if contact has image
	 */
	public void setHasImage(final boolean hasImage) {
		this.hasImage = hasImage;
	}

	/**
	 *
	 * @return the channels
	 */
	public List<Channel> getChannels() {
		if (channels == null) {
			channels = new ArrayList<>();
		}
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
	 * @return the assigned position keys
	 */
	@Override
	public List<String> getPositionIds() {
		if (positionIds == null) {
			positionIds = new ArrayList<>();
		}
		return positionIds;
	}

	/**
	 *
	 * @param positionIds the assigned position keys
	 */
	@Override
	public void setPositionIds(final List<String> positionIds) {
		this.positionIds = positionIds;
	}

	@Override
	public Integer getVersionId() {
		return versionId;
	}

	@Override
	public void setVersionId(final Integer versionId) {
		this.versionId = versionId;
	}

}
