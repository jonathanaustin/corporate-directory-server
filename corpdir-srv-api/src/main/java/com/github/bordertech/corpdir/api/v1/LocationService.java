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

	ServiceResponse<List<Location>> getLocations(final String search, final Boolean topLevel);

	ServiceResponse<Location> getLocation(final String locationKeyId);

	ServiceResponse<Location> createLocation(final Location locationKeyId);

	ServiceResponse<Location> updateLocation(final String locationKeyId, final Location location);

	ServiceBasicResponse deleteLocation(final String locationKeyId);

	ServiceResponse<List<Location>> getSubLocations(final String locationKeyId);

	ServiceResponse<Location> addSubLocation(final String locationKeyId, final String subLocationKeyId);

	ServiceResponse<Location> removeSubLocation(final String locationKeyId, final String subLocationKeyId);

}
