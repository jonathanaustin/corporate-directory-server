package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.smart.panel.LocationPanel;
import com.github.bordertech.flux.wc.app.view.dumb.list.TreeSelectView;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends DefaultCrudSmartView {

	public LocationCrudView() {
		super("LOC", "Location", new LocationPanel("PL"), new TreeSelectView("SEL"));
	}

}
