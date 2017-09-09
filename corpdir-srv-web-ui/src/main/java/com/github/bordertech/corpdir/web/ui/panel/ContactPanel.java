package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.model.ApiModelUtil;
import com.github.bordertech.corpdir.web.ui.model.SearchVersionKey;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.lib.app.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.combo.AddRemoveListView;
import com.github.bordertech.wcomponents.lib.app.combo.PollingSelectView;
import com.github.bordertech.wcomponents.lib.app.combo.SelectWithCriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.combo.SelectWithCriteriaView;
import java.util.List;

/**
 * Contact detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactPanel extends BasicApiKeyPanel<Contact> {

	private final PollingSelectView selectView;

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

		// Positions
		getFormPanel().add(new WHeading(HeadingLevel.H2, "Positions"));

		// Setup the select and find view
		selectView = new PollingSelectView(new SelectMenuView());
		SelectWithCriteriaView findView = new SelectWithCriteriaTextView();
		AddRemoveListView posView = new AddRemoveListView("pos", selectView, findView);
		getFormPanel().add(posView);

		// Models
		selectView.setSearchModelKey("contact.positions.search");
		findView.setSearchModelKey("position.search");

	}

	@Override
	protected void initViewContent(final Request request) {
		Contact bean = getViewBean();
		if (!bean.getPositionIds().isEmpty()) {
			selectView.doStartPolling(new SearchVersionKey(null, bean.getId()));
		}
		super.initViewContent(request);
	}

	@Override
	public void updateBeanValue() {
		super.updateBeanValue();
		Contact bean = getViewBean();
		List<Position> positions = (List<Position>) selectView.getListView().getBeanValue();
		bean.setPositionIds(ApiModelUtil.convertApiObjectsToIds(positions));
	}

}
