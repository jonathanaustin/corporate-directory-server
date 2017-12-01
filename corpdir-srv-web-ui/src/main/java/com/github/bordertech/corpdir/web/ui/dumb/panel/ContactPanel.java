package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLabel;

/**
 * Contact detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactPanel extends BasicApiKeyPanel<Contact> {

	private final PollingDropdownOptionsView<String, Location> drpLocation = new PollingDropdownOptionsView("LOC");
	private final PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");

// TODO
//	private boolean hasImage;
//	private List<Channel> channels;
	/**
	 * Construct basic contact panel.
	 *
	 * @param viewId the viewId
	 */
	public ContactPanel(final String viewId) {
		super(viewId, false);
		// Contact Details
		addTextField("Business key", "businessKey", true);
		addTextField("First name", "firstName", true);
		addTextField("Last name", "lastName", true);
		addTextField("Company", "companyTitle", false);
		addTextField("Description", "description", true);
		addCheckBox("Active", "active", false);

		getFormPanel().add(new WHeading(HeadingLevel.H2, "Address"));
		AddressPanel addressPanel = new AddressPanel("ADDR");
		addressPanel.setBeanProperty("address");
		getFormPanel().add(addressPanel);

		// Location
		WLabel lbl = new WLabel("Location", drpLocation.getSelectInput());
		getFormLayout().addField(lbl, drpLocation);
		drpLocation.setIncludeNullOption(true);
		drpLocation.setCodeProperty("id");
		drpLocation.getOptionsView().setBeanProperty("locationId");
		drpLocation.setStoreKey(DataApiType.LOCATION.getSearchStoreKey());
		// FIXME JA
//		drpLocation.setRetrieveListModelKey("location.search");

		// Assigned Positions
		lbl = new WLabel("Assigned positions", multiPos.getSelectInput());
		getFormLayout().addField(lbl, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setStoreKey(DataApiType.POSITION.getSearchStoreKey());
		// FIXME JA
//		multiPos.setRetrieveListModelKey("position.search");

	}

}
