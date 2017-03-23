package com.github.bordertech.corpdir.jpa.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class LocationEntity extends AbstractPersistentObject {

	@ManyToOne
	@JoinColumn(name = "parentLocation_Id")
	private LocationEntity parentLocation;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentLocation")
	private Set<LocationEntity> subLocations;

	@Embedded
	private AddressEntity address;

	private String description;

	/**
	 * Default constructor.
	 */
	protected LocationEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 * @param businessKey the business key.
	 */
	public LocationEntity(final Long id, final String businessKey) {
		super(id, businessKey);
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
	public Set<LocationEntity> getSubLocations() {
		return subLocations;
	}

	/**
	 * Add a sub location.
	 *
	 * @param location the sub location to add
	 */
	public void addSubLocation(final LocationEntity location) {
		if (subLocations == null) {
			subLocations = new HashSet<>();
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

}
