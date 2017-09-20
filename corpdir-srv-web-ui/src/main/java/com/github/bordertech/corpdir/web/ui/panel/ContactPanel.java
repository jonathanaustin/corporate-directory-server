package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingDropdownOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingMultiSelectPairOptionsView;

/**
 * Contact detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ContactPanel extends BasicApiKeyPanel<Contact> {

	private final PollingDropdownOptionsView<String, Location> drpLocation = new PollingDropdownOptionsView<>("LOC");
	private final PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");

// TODO
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

		// Location
		WLabel lbl = new WLabel("Location", drpLocation.getSelectInput());
		getFormLayout().addField(lbl, drpLocation);
		drpLocation.setIncludeNullOption(true);
		drpLocation.setCodeProperty("id");
		drpLocation.getOptionsView().setBeanProperty("locatinoId");
		drpLocation.setRetrieveCollectionModelKey("location.search");

		// Assigned Positions
		lbl = new WLabel("Assigned positions", multiPos.getSelectInput());
		getFormLayout().addField(lbl, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setRetrieveCollectionModelKey("position.search");

	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		drpLocation.startLoad("");
		multiPos.startLoad("");
	}

//	private void setupSearch(){
//
//		// Positions
//		getFormPanel().add(new WHeading(HeadingLevel.H2, "Positions"));
//
//		// Setup the select and find view
//		selectView = new PollingSelectView(new WSingleSelectView());
//		final SelectWithSearchView findView = new SelectWithSearchTextView() {
//			@Override
//			public void configAjax(final View view) {
//				super.configAjax(view);
//				// Make the BACK button only have one AJAX Target
//				if (view == getToolbarView()) {
//					view.clearEventAjaxTargets(NavigationEventType.BACK);
//					view.addEventAjaxTarget(selectView, NavigationEventType.BACK);
//				}
//			}
//		};
//
//		AddDeleteListView posView = new AddDeleteListView("pos", selectView, findView);
//		getFormPanel().add(posView);
//		// Setup dialog
//		posView.getDialog().setTitle("Search Positions");
//
//		// Models
//		selectView.setRetrieveCollectionModelKey("contact.positions.search");
//		findView.setRetrieveCollectionModelKey("position.search");
//
//		// Use the back button
//		findView.getToolbarView().addToolbarItem(ToolbarNavigationItem.BACK);
//	}
//	@Override
//	protected void initViewContent(final Request request) {
//		Contact bean = getViewBean();
//		// Positions
//		if (!bean.getPositionIds().isEmpty()) {
//			dispatchEvent(PollingEventType.START_POLLING, new SearchVersionKey(null, bean.getId()));
////			selectView.doStartPolling();
//		}
//		super.initViewContent(request);
//	}
//
//	@Override
//	public void updateBeanValue() {
//		super.updateBeanValue();
//		Contact bean = getViewBean();
//		// Positions
//		List<Position> positions = (List<Position>) selectView.getCollectionView().getBeanValue();
//		bean.setPositionIds(ApiModelUtil.convertApiObjectsToIds(positions));
//	}
}
