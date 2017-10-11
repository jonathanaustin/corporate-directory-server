package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.web.ui.model.DefaultModelTreeService;
import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;

/**
 *
 * @author jonathan
 */
public class LocationTreeModel extends DefaultModelTreeService<Location> {

	public LocationTreeModel() {
		super(LocationService.class);
	}

}
