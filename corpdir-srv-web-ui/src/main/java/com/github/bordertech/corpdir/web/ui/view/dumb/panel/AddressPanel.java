package com.github.bordertech.corpdir.web.ui.view.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Address;
import com.github.bordertech.corpdir.web.ui.view.dumb.BasicApiPanel;

/**
 * Address panel.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddressPanel extends BasicApiPanel<Address> {

	/**
	 * Construct basic address panel.
	 *
	 * @param viewId the viewId
	 */
	public AddressPanel(final String viewId) {
		super(viewId);
		// Address Details
		addTextField("Work station", "workStation", false);
		addTextField("Line 1", "line1", false);
		addTextField("Line 2", "line2", false);
		addTextField("Suburb", "suburb", false);
		addTextField("State", "state", false);
		addTextField("Postcode", "postcode", false);
		addTextField("Country", "country", false);
	}

}
