package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;

/**
 * Location CRUD API implementation.
 *
 * @author jonathan
 */
public class LocationApiImpl extends DefaultCorpCrudTreeApi<Location, LocationService> implements LocationApi {

	@Inject
	public LocationApiImpl(final LocationService service) {
		super(Location.class, service);
	}

}
