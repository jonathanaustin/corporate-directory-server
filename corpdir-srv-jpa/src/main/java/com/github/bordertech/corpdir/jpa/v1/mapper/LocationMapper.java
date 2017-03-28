package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.jpa.common.MapperUtil;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.jpa.v1.entity.LocationEntity;

/**
 * Map {@link Location} and {@link LocationEntity}.
 *
 * @author jonathan
 */
public final class LocationMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private LocationMapper() {
		// prevent instatiation
	}

	/**
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Location convertEntityToApi(final LocationEntity from) {
		if (from == null) {
			return null;
		}
		Location to = new Location();
		MapperUtil.handleCommonEntityToApi(from, to);
		to.setDescription(from.getDescription());
		to.setAddress(AddressMapper.convertEntityToApi(from.getAddress()));
		to.setSubLocationKeys(MapperUtil.convertEntitiesToApiIDs(from.getSubLocations()));
		return to;
	}

}
