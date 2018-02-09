package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.LocationActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.DefaultCorpCrudTreeActionCreator;
import javax.inject.Inject;

/**
 * Location action creator implementation.
 *
 * @author jonathan
 */
public class LocationActionCreatorImpl extends DefaultCorpCrudTreeActionCreator<Location, LocationApi> implements LocationActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public LocationActionCreatorImpl(final LocationApi api) {
		super(CorpEntityType.LOCATION, api);
	}
}
