package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.api.v1.model.Location;
import java.util.List;

/**
 * Location Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface LocationService {

	ServiceResponse<Location> getLocation(final String locationKeyId);

	ServiceResponse<List<Location>> getSubLocations(final String locationKeyId);

	ServiceResponse<Location> createLocation(final Location locationKeyId);

	ServiceResponse<Location> updateLocation(final String locationKeyId, final Location location);

	ServiceBasicResponse deleteLocation(final String locationKeyId);

	ServiceBasicResponse assignLocationToLocation(final String locationKeyId, final String parentLocationKeyId);

}
