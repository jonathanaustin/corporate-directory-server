package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.LocationPanel;
import com.github.bordertech.wcomponents.lib.app.view.combo.DefaultCrudView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends DefaultCrudView {

	public LocationCrudView() {
		super("Location", new LocationPanel());
		setSearchModelKey("location.search");
		setActionModelKey("location.action");
	}

}
