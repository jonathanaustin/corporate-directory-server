package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.LocationModel;
import com.github.bordertech.corpdir.web.ui.panel.LocationPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView2;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends DefaultCrudView2 {

	private static final LocationModel MODEL = new LocationModel();

	public LocationCrudView(final String qualifier) {
		super("Location", MODEL, new LocationPanel(), qualifier);
	}

}
