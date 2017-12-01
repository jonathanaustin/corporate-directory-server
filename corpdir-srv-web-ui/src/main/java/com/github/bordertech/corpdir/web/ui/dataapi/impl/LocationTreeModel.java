package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelTreeService;

/**
 *
 * @author jonathan
 */
public class LocationTreeModel extends DefaultModelTreeService<Location, LocationService> {

	public LocationTreeModel() {
		super(Location.class, LocationService.class);
	}

}
