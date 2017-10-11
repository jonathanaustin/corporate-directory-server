package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.model.DefaultModelSearchActionService;

/**
 * Location search and action model.
 *
 * @author jonathan
 */
public class LocationModel extends DefaultModelSearchActionService<Location> {

	public LocationModel() {
		super(Location.class, LocationService.class);
	}

}
