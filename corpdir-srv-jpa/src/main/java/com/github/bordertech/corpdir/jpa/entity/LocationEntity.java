package com.github.bordertech.corpdir.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Location of contact.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Location")
public class LocationEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "parentLocation_Id")
	private LocationEntity parentLocation;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentLocation")
	private List<LocationEntity> subLocations;

	@Embedded
	private AddressEntity address;

	private String alternateKey;
	private String description;
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
	 * @return the parent location
	 */
	public LocationEntity getParentLocation() {
		return parentLocation;
	}

	/**
	 * @param parentLocation the parent location
	 */
	public void setParentLocation(final LocationEntity parentLocation) {
		this.parentLocation = parentLocation;
	}

	/**
	 *
	 * @return the alternate location key
	 */
	public String getAlternateKey() {
		return alternateKey;
	}

	/**
	 *
	 * @param alternateKey the alternate location key
	 */
	public void setAlternateKey(final String alternateKey) {
		this.alternateKey = alternateKey;
	}

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
	 *
	 * @return the sub locations
	 */
	public List<LocationEntity> getSubLocations() {
		return subLocations;
	}

	/**
	 * Add a sub location.
	 *
	 * @param location the sub location to add
	 */
	public void addSubLocation(final LocationEntity location) {
		if (subLocations == null) {
			subLocations = new ArrayList<>();
		}
		subLocations.add(location);
		location.setParentLocation(this);
	}

	/**
	 * Remove a sub location.
	 *
	 * @param location the sub location to remove
	 */
	public void removeSubLocation(final LocationEntity location) {
		if (subLocations != null) {
			subLocations.remove(location);
		}
		location.setParentLocation(null);
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
