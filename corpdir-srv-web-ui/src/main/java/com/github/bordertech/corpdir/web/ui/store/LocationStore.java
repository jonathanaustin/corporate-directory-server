package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;

/**
 * Location Store with backing API.
 *
 * @author jonathan
 */
public interface LocationStore extends CorpCrudTreeStore<Location, LocationApi> {
}
