package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.LocationPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudTreeSmartView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends AppSecureCrudWrapperView<String, Location> {

	public LocationCrudView() {
		super("LOC", CardType.LOCATION, new DefaultCrudTreeSmartView<String, String, Location>("SV", "Location", new LocationPanel("PL")));
	}

}
