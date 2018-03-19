package com.github.bordertech.corpdir.web.ui.view.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.view.dumb.panel.LocationPanel;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpCrudTreeSmartView;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpSecureCrudCardView;

/**
 * Location CRUD tree view.
 *
 * @author jonathan
 */
public class LocationCardTreeView extends DefaultCorpSecureCrudCardView<Location> {

	public LocationCardTreeView() {
		super("LOC", CardType.LOCATION, new DefaultCorpCrudTreeSmartView<Location>("SV", "Location", new LocationPanel("PL")));
	}

}
