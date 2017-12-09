package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.LocationPanel;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends AppSecureCrudTreeView<String, Location> {

	public LocationCrudView() {
		super(CardType.LOCATION_CARD, "LOC", "Location", new LocationPanel("PL"));
	}

}
