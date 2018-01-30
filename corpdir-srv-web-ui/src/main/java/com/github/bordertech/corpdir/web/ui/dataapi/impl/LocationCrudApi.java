package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudTreeDataApi;

/**
 *
 * @author jonathan
 */
public class LocationCrudApi extends DefaultCrudTreeDataApi<Location, LocationService> {

	public LocationCrudApi() {
		super(Location.class, LocationService.class);
	}

}
