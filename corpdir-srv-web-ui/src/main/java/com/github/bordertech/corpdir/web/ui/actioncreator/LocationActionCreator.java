package com.github.bordertech.corpdir.web.ui.actioncreator;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.CorpCrudTreeActionCreator;

/**
 * Location CRUD ActionCreator.
 *
 * @author jonathan
 */
public interface LocationActionCreator extends CorpCrudTreeActionCreator<Location, LocationApi> {
}
