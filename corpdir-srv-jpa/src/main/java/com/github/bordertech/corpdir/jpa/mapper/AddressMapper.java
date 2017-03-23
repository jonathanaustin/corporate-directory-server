package com.github.bordertech.corpdir.jpa.mapper;

import com.github.bordertech.corpdir.api.data.Address;
import com.github.bordertech.corpdir.jpa.entity.AddressEntity;

/**
 * Map {@link Address} and {@link AddressEntity}.
 *
 * @author jonathan
 */
public final class AddressMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private AddressMapper() {
		// prevent instatiation
	}

	/**
	 * @param from the API item
	 * @return the entity item
	 */
	public static AddressEntity convertApiToEntity(final Address from) {
		if (from == null) {
			return null;
		}
		AddressEntity to = new AddressEntity();
		to.setWorkStation(from.getWorkStation());
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setCountry(from.getCountry());
		to.setPostcode(from.getPostcode());
		to.setState(from.getState());
		to.setSuburb(from.getSuburb());
		return to;
	}

	/**
	 * @param from the entity item
	 * @return the API item
	 */
	public static Address convertEntityToApi(final AddressEntity from) {
		if (from == null) {
			return null;
		}
		Address to = new Address();
		to.setWorkStation(from.getWorkStation());
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setCountry(from.getCountry());
		to.setPostcode(from.getPostcode());
		to.setState(from.getState());
		to.setSuburb(from.getSuburb());
		return to;
	}

}
