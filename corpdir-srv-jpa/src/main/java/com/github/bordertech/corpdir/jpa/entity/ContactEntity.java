package com.github.bordertech.corpdir.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Contact details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Contact")
public class ContactEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ChannelEntity> channels;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ImageEntity image;

	@ManyToOne(fetch = FetchType.EAGER)
	private LocationEntity location;

	@Embedded
	private AddressEntity address;

	private String alternateKey;
	private String firstName;
	private String lastName;
	private String companyTitle;
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
	 * @param id the unique id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the alternate contact key
	 */
	public String getAlternateKey() {
		return alternateKey;
	}

	/**
	 *
	 * @param alternateKey the alternate contact key
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
	public AddressEntity getAddress() {
		return address;
	}

	/**
	 *
	 * @param address the address
	 */
	public void setAddress(final AddressEntity address) {
		this.address = address;
	}

	/**
	 * @return the location
	 */
	public LocationEntity getLocation() {
		return location;
	}

	/**
	 * @param location the location
	 */
	public void setLocation(final LocationEntity location) {
		this.location = location;
	}

	/**
	 *
	 * @return the channels
	 */
	public List<ChannelEntity> getChannels() {
		return channels;
	}

	/**
	 * @param channel the channel to add
	 */
	public void addChannel(final ChannelEntity channel) {
		if (channels == null) {
			channels = new ArrayList<>();
		}
		channels.add(channel);
	}

	/**
	 * @param channel the channel to add
	 */
	public void removeChannel(final ChannelEntity channel) {
		if (channels != null) {
			channels.remove(channel);
		}
	}

	/**
	 *
	 * @return the contact image
	 */
	public ImageEntity getImage() {
		return image;
	}

	/**
	 *
	 * @param image the contact image
	 */
	public void setImage(final ImageEntity image) {
		this.image = image;
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
