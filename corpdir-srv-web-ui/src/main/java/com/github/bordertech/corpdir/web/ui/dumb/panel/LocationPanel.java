package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiTreeablePanel;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WHeading;

/**
 * Location detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LocationPanel extends BasicApiTreeablePanel<Location> {

	/**
	 * Construct basic location panel.
	 *
	 * @param viewId the viewId
	 */
	public LocationPanel(final String viewId) {
		super("Location", viewId);

		getFormPanel().add(new WHeading(HeadingLevel.H2, "Address"));
		AddressPanel addressPanel = new AddressPanel("ADDR");
		addressPanel.setBeanProperty("address");
		getFormPanel().add(addressPanel);
	}
}
