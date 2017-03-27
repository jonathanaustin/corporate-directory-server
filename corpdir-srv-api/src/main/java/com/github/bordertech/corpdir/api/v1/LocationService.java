package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.v1.model.Location;
import java.util.List;

/**
 * Location Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface LocationService {

	Location getLocation(final String locationKeyId);

	List<Location> getSubLocations(final String locationKeyId);

	String createLocation(final Location locationKeyId);

	Location updateLocation(final String locationKeyId, final Location location);

	void deleteLocation(final String locationKeyId);

	void assignLocationToLocation(final String locationKeyId, final String parentLocationKeyId);

}
