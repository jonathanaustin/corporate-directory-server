package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.Location;
import java.util.List;

/**
 * Location Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface LocationService {

	DataResponse<List<Location>> getLocations(final String search, final Boolean assigned);

	DataResponse<Location> getLocation(final String locationKeyId);

	DataResponse<Location> createLocation(final Location locationKeyId);

	DataResponse<Location> updateLocation(final String locationKeyId, final Location location);

	BasicResponse deleteLocation(final String locationKeyId);

	DataResponse<List<Location>> getSubLocations(final String locationKeyId);

	DataResponse<Location> addSubLocation(final String locationKeyId, final String subLocationKeyId);

	DataResponse<Location> removeSubLocation(final String locationKeyId, final String subLocationKeyId);

}
