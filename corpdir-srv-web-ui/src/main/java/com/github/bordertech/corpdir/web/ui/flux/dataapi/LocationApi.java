package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;

/**
 * Location CRUD API.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface LocationApi extends CorpCrudTreeDataApi<Location, LocationService> {
}
