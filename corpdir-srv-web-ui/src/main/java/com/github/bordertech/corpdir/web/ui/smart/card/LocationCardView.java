package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.LocationPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudSmartView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCardView extends AppSecureCrudCardView<Location> {

	public LocationCardView() {
		super("LOC", CardType.LOCATION, new DefaultCorpCrudSmartView<Location>("SV", "Location", new LocationPanel("PL")));
	}

}
