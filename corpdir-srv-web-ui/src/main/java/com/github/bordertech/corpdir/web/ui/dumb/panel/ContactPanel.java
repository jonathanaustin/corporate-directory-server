package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WebUtilities;

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

		// Address
		getFormPanel().add(new WHeading(HeadingLevel.H2, "Address"));
		AddressPanel addressPanel = new AddressPanel("ADDR");
		addressPanel.setBeanProperty("address");
		getFormPanel().add(addressPanel);

		// Location
		PollingDropdownOptionsView<String, Location> drpLocation = new PollingDropdownOptionsView("LOC");
		WLabel lbl = new WLabel("Location", drpLocation.getSelectInput());
		getFormLayout().addField(lbl, drpLocation);
		drpLocation.setIncludeNullOption(true);
		drpLocation.setCodeProperty("id");
		drpLocation.getOptionsView().setBeanProperty("locationId");
		drpLocation.setStoreKey(DataApiType.LOCATION.getSearchStoreKey());

		// Assigned Positions
		PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");
		lbl = new WLabel("Assigned positions", multiPos.getSelectInput());
		getFormLayout().addField(lbl, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setStoreKey(DataApiType.POSITION.getSearchStoreKey());

	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		Contact contact = getViewBean();
		channelPanel.setViewBean(contact.getChannels());
	}

	@Override
	public void updateBeanValue() {
		super.updateBeanValue();
		Contact contact = getViewBean();
		WebUtilities.updateBeanValue(channelPanel);
		contact.setChannels(channelPanel.getViewBean());
	}

}
