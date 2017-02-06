package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.Location;
import java.util.List;

/**
 * Location Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface LocationService {

	Location getLocation(final Long locationId);

	Location getLocation(final String locationAltKey);

	List<Location> getSubLocations(final Long locationId);

	List<Location> getSubLocations(final String locationAltKey);

	Long createLocation(final Location location);

	Location updateLocation(final Location location);

	void deleteLocation(final Long locationId);

	void assignLocationToLocation(final Long locationId, final Long parentLocationId);

}
