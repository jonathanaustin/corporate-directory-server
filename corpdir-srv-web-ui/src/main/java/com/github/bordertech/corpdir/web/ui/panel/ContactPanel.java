package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.common.BasicApiKeyPanel;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WHeading;

/**
 * Contact detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactPanel extends BasicApiKeyPanel<Contact> {

// TODO
//	private String locationId;
//	private boolean hasImage;
//	private List<Channel> channels;
//	private List<String> positionIds;
	/**
	 * Construct basic detail panel. \
	 */
	public ContactPanel() {
		super(false);
		// Contact Details
		addTextField("Business key", "businessKey", true);
		addTextField("First name", "firstName", true);
		addTextField("Last name", "lastName", true);
		addTextField("Company", "companyTitle", false);
		addTextField("Description", "description", true);
		addCheckBox("Active", "active", false);

		getFormPanel().add(new WHeading(HeadingLevel.H2, "Address"));
		AddressPanel addressPanel = new AddressPanel();
		addressPanel.setBeanProperty("address");
		getFormPanel().add(addressPanel);

//		getFormPanel().add(new WHeading(HeadingLevel.H2, "Channel"));
//		ChannelCrudView channelView = new ChannelCrudView("WW");
//		add(channelView);
	}

}
