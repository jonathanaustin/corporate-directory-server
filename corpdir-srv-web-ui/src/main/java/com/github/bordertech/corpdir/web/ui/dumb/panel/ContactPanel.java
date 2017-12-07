package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.corpdir.web.ui.dumb.input.DropdownEntityLinkView;
import com.github.bordertech.corpdir.web.ui.dumb.input.MultiSelectPairEntityLinkView;
import com.github.bordertech.flux.wc.view.smart.input.DefaultPollingMultiSelectOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.DefaultPollingSingleSelectOptionsView;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WCollapsible;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLabel;

/**
 * Contact detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactPanel extends BasicApiKeyPanel<Contact> {

	private final ChannelTablePanel channelPanel = new ChannelTablePanel("CHNL");

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

		// Channels
		getFormPanel().add(new WHeading(HeadingLevel.H2, "Channels"));
		getFormPanel().add(channelPanel);
		channelPanel.setBeanProperty("channels");
		channelPanel.setSearchAncestors(true);

		// Location
//		PollingDropdownOptionsView<String, Location> drpLocation = new PollingDropdownOptionsView("LOC");
		DropdownEntityLinkView<Location> locView = new DropdownEntityLinkView<>("LOCL", CardType.LOCATION_CARD);
		DefaultPollingSingleSelectOptionsView<String, Location> drpLocation = new DefaultPollingSingleSelectOptionsView("LOC", locView);
		WLabel lbl = new WLabel("Location", drpLocation.getSelectInput());
		getFormLayout().addField(lbl, drpLocation);
		drpLocation.setIncludeNullOption(true);
		drpLocation.setCodeProperty("id");
		drpLocation.getOptionsView().setBeanProperty("locationId");
		drpLocation.setStoreKey(DataApiType.LOCATION.getSearchStoreKey());

		// Assigned Positions
//		PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");
		MultiSelectPairEntityLinkView posView = new MultiSelectPairEntityLinkView("POSL", CardType.POSITION_CARD);
		DefaultPollingMultiSelectOptionsView multiPos = new DefaultPollingMultiSelectOptionsView("POS", posView);
		lbl = new WLabel("Assigned positions", multiPos.getSelectInput());
		getFormLayout().addField(lbl, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setStoreKey(DataApiType.POSITION.getSearchStoreKey());

		// Address
		AddressPanel addressPanel = new AddressPanel("ADDR");
		addressPanel.setBeanProperty("address");
		WCollapsible addrColl = new WCollapsible(addressPanel, "Addresss", WCollapsible.CollapsibleMode.CLIENT);
		getFormPanel().add(addrColl);

	}

}
