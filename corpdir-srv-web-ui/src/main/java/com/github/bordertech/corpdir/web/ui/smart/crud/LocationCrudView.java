package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.smart.panel.LocationPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudTreeSmartView;
import com.github.bordertech.flux.wc.view.smart.tree.ListOrTreeSelectView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends DefaultCrudTreeSmartView<String, Location> {

	public LocationCrudView() {
		super("LOC", "Location", new LocationPanel("PL"));
	}

	@Override
	protected ListOrTreeSelectView<Location> getSelectView() {
		return super.getSelectView(); //To change body of generated methods, choose Tools | Templates.
	}

}
