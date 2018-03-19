package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.DefaultCorpCrudTreeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.LocationApi;
import javax.inject.Inject;

/**
 * Location action creator implementation.
 *
 * @author jonathan
 */
public class LocationActionCreator extends DefaultCorpCrudTreeActionCreator<Location, LocationApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public LocationActionCreator(final LocationApi api) {
		super(CorpEntityType.LOCATION, api);
	}
}
