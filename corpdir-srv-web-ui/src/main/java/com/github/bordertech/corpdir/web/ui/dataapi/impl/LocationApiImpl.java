package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudTreeDataApi;
import javax.inject.Inject;

/**
 * Location CRUD API implementation.
 *
 * @author jonathan
 */
public class LocationApiImpl extends DefaultCorpCrudTreeDataApi<Location, LocationService> implements LocationApi {

	@Inject
	public LocationApiImpl(final LocationService service) {
		super(Location.class, service);
	}

}
