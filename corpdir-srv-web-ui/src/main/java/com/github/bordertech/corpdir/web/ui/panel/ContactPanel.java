package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.model.SearchVersionKey;
import com.github.bordertech.corpdir.web.ui.util.ApiModelUtil;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.lib.app.event.NavigationEventType;
import com.github.bordertech.wcomponents.lib.app.view.combo.AddDeleteListView;
import com.github.bordertech.wcomponents.lib.app.view.combo.PollingSelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.combo.SelectWithCriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.view.combo.SelectWithCriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.list.WSingleSelectView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.ToolbarNavigationItem;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.List;

/**
 * Contact detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactPanel extends BasicApiKeyPanel<Contact> {

	private final PollingSelectSingleView selectView;

// TODO
//	private String locationId;
//	private boolean hasImage;
//	private List<Channel> channels;
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
		selectView = new PollingSelectSingleView(new WSingleSelectView());
		final SelectWithCriteriaView findView = new SelectWithCriteriaTextView() {
			@Override
			public void configAjax(final View view) {
				super.configAjax(view);
				// Make the BACK button only have one AJAX Target
				if (view == getToolbarView()) {
					view.clearEventAjaxTargets(NavigationEventType.BACK);
					view.addEventAjaxTarget(selectView, NavigationEventType.BACK);
				}
			}
		};

		AddDeleteListView posView = new AddDeleteListView("pos", selectView, findView);
		getFormPanel().add(posView);
		// Setup dialog
		posView.getDialog().setTitle("Search Positions");

		// Models
		selectView.setRetrieveCollectionModelKey("contact.positions.search");
		findView.setRetrieveCollectionModelKey("position.search");

		// Use the back button
		findView.getToolbarView().addToolbarItem(ToolbarNavigationItem.BACK);

	}

	protected void initViewContent(final Request request) {
		Contact bean = getViewBean();
		// Positions
		if (!bean.getPositionIds().isEmpty()) {
			selectView.doStartPolling(new SearchVersionKey(null, bean.getId()));
		}
		super.initViewContent(request);
	}

	@Override
	public void updateBeanValue() {
		super.updateBeanValue();
		Contact bean = getViewBean();
		// Positions
		List<Position> positions = (List<Position>) selectView.getCollectionView().getBeanValue();
		bean.setPositionIds(ApiModelUtil.convertApiObjectsToIds(positions));
	}

}
