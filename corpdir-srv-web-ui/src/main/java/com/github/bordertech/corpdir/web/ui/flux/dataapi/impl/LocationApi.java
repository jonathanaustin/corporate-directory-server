package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.DefaultCorpCrudTreeDataApi;
import javax.inject.Inject;

/**
 * Location CRUD API implementation.
 *
 * @author jonathan
 */
public class LocationApi extends DefaultCorpCrudTreeDataApi<Location, LocationService> {

	@Inject
	public LocationApi(final LocationService service) {
		super(Location.class, service);
	}

}
