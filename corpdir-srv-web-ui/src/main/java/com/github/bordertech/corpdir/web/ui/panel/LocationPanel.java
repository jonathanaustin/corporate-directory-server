package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.common.BasicApiKeyPanel;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WHeading;

/**
 * Location detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LocationPanel extends BasicApiKeyPanel<Location> {

	/**
	 * Construct basic detail panel. \
	 */
	public LocationPanel() {
		getFormPanel().add(new WHeading(HeadingLevel.H2, "Address"));
		AddressPanel addressPanel = new AddressPanel();
		addressPanel.setBeanProperty("address");
		getFormPanel().add(addressPanel);
	}

}
