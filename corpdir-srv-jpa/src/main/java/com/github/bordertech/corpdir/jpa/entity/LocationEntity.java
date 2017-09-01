package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultKeyIdTreeObject;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Location of contact.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Location")
public class LocationEntity extends DefaultKeyIdTreeObject<LocationEntity> {

	@Embedded
	private AddressEntity address;

	/**
	 * Default constructor.
	 */
	protected LocationEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public LocationEntity(final Long id) {
		super(id);
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

}
