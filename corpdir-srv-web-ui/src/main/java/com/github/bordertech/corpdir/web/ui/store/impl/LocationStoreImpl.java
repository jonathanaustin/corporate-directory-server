package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.DefaultCorpCrudTreeStore;
import com.github.bordertech.corpdir.web.ui.store.LocationStore;
import javax.inject.Inject;

/**
 * Location Store with backing API implementation.
 *
 * @author jonathan
 */
public class LocationStoreImpl extends DefaultCorpCrudTreeStore<Location, LocationApi> implements LocationStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public LocationStoreImpl(final LocationApi api) {
		super(CorpEntityType.LOCATION, api);
	}
}
