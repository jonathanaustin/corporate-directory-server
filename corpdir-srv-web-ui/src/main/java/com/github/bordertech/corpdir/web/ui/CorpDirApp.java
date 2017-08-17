package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.view.BasicEntityPanel;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTimeoutWarning;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.impl.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.impl.EntityActionMenuView;
import com.github.bordertech.wcomponents.lib.app.impl.EntityBaseView;
import com.github.bordertech.wcomponents.lib.app.impl.EntityWithActionView;
import com.github.bordertech.wcomponents.lib.app.impl.ListBasicView;
import com.github.bordertech.wcomponents.lib.app.impl.ListWithCriteriaView;
import com.github.bordertech.wcomponents.lib.app.impl.SelectListMenuView;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.ServiceModel;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultDispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.impl.WController;
import com.github.bordertech.wcomponents.lib.flux.impl.WDispatcher;
import com.github.bordertech.wcomponents.lib.polling.PollingServiceView;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Corporate Directory Admin UI.
 *
 * @author jonathan
 */
public class CorpDirApp extends WApplication implements MessageContainer {

	/**
	 * Messages.
	 */
	private final WMessages messages = new WMessages();

	/**
	 * Card manager.
	 */
	private final WCardManager mgr = new WCardManager();

	private final WDispatcher dispatcher = new DefaultDispatcher();

	/**
	 * Construct Application.
	 */
	public CorpDirApp() {

		add(dispatcher);

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Corporate Directory"));

		// Detail
		WPanel detail = new WPanel();
		detail.setMargin(new Margin(Size.LARGE));
		add(detail);

		// Messages
		detail.add(messages);

		// Card manager
		detail.add(mgr);

		WButton button = new WButton("Reset");
		button.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatcher.dispatch(new Event(ActionEventType.RESET));
			}
		});

		//-----------
		// View 1
		CriteriaView<String> critView = new CriteriaTextView(dispatcher, "A");
		ListView<String> listView = new ListBasicView(dispatcher, "A");
		ListWithCriteriaView<String, String> view = new ListWithCriteriaView<>(dispatcher, "A", critView, listView);
		// Set Model
		view.setServiceModel(new MyStringSearchModel());

		//-----------
		// View 2
		EntityView<OrgUnit> entView = new EntityBaseView<>(dispatcher, "B");
		EntityActionView menuView = new EntityActionMenuView(dispatcher, "B");
		EntityWithActionView<OrgUnit> view2 = new EntityWithActionView<>(dispatcher, "B", entView, menuView);
		entView.getContent().add(new BasicEntityPanel());
		// Set Model
		view2.setActionModel(new MyOrgUnitServiceModel());

		//-----------
		// View 3
		CriteriaView<String> critView2 = new CriteriaTextView(dispatcher, "X");
		ListView<OrgUnit> listView2 = new SelectListMenuView(dispatcher, "X");
		ListWithCriteriaView<String, OrgUnit> view3 = new ListWithCriteriaView<>(dispatcher, "X", critView2, listView2);
		// Set Model
		view3.setServiceModel(new MyOrgUnitSearchModel());

		//-----------
		// View4 Just do it ALL Yourself
		DefaultView view4 = new DefaultView(dispatcher, "Y");
		// Views
		CriteriaView<String> critView4 = new CriteriaTextView(dispatcher, "Y");
		ListView<OrgUnit> listView4 = new SelectListMenuView(dispatcher, "Y");
		PollingServiceView<String, List<OrgUnit>> pollingView4 = new PollingServiceView<>(dispatcher, "Y");

		// Set views on Controller
		ListWithCriteriaCtrl<String, OrgUnit> ctrl4 = new ListWithCriteriaCtrl<>(dispatcher, "Y");
		ctrl4.setCriteriaView(critView4);
		ctrl4.setPollingView(pollingView4);
		ctrl4.setListView(listView4);
		// Set Model
		ctrl4.setServiceModel(new MyOrgUnitSearchModel());

		// Add views to holder
		WDiv holder = view4.getContent();
		holder.add(ctrl4);
		holder.add(critView4);
		holder.add(pollingView4);
		holder.add(listView4);

		//-----------
		// MAIN Controller
		WController ctrl = new DefaultController(dispatcher);
		ctrl.addView(view);
		ctrl.addView(view2);
		ctrl.addView(view3);
		ctrl.addView(view4);

		WDiv div = new WDiv();
		div.add(ctrl);
		div.add(button);
		div.add(wrapInSection((WDiv) view, "View 1"));
		div.add(wrapInSection((WDiv) view2, "View 2"));
		div.add(wrapInSection((WDiv) view3, "View 3"));
		div.add(wrapInSection((WDiv) view4, "View 4"));
		mgr.add(div);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WText(new Date().toString()));

		add(new WTimeoutWarning());

		// IDs
		header.setIdName("hdr");
		messages.setIdName("msgs");
	}

	public static class MyStringSearchModel implements ServiceModel<String, List<String>> {

		@Override
		public List<String> service(String criteria) {
			if ("error".equals(criteria)) {
				throw new SystemException("Big error");
			}
			try {
				Thread.sleep(3000);
			} catch (Exception e) {

			}
			if ("error2".equals(criteria)) {
				throw new SystemException("Big error2");
			}
			List<String> items = new ArrayList<>();
			items.add("A1");
			items.add("A2");
			items.add("A3");
			items.add("A4");
			items.add("A5");
			return items;
		}
	}

	public static class MyOrgUnitSearchModel implements ServiceModel<String, List<OrgUnit>> {

		@Override
		public List<OrgUnit> service(final String criteria) {
			if ("error".equals(criteria)) {
				throw new SystemException("Big error");
			}
			try {
				Thread.sleep(3000);
			} catch (Exception e) {

			}
			if ("error2".equals(criteria)) {
				throw new SystemException("Big error2");
			}
			List<OrgUnit> items = new ArrayList<>();
			for (int i = 1; i < 5; i++) {
				OrgUnit item = new OrgUnit();
				item.setBusinessKey("A" + i);
				item.setDescription("DESC" + i);
				item.setId("A" + i);
				items.add(item);
			}
			return items;
		}
	}

	public static class MyOrgUnitServiceModel implements ActionModel<OrgUnit> {

		@Override
		public OrgUnit save(final OrgUnit entity) {
			entity.setDescription("Been in save:" + new Date().toString());
			return entity;
		}

		@Override
		public OrgUnit update(final OrgUnit entity) {
			entity.setDescription("Been in update:" + new Date().toString());
			return entity;
		}

		@Override
		public void delete(final OrgUnit entity) {
			// Do nothing
		}

		@Override
		public OrgUnit refresh(final OrgUnit entity) {
			entity.setDescription("Been in refresh:" + new Date().toString());
			return entity;
		}

		@Override
		public OrgUnit createInstance() {
			return new OrgUnit();
		}

	}

	protected final WSection wrapInSection(final WDiv div, final String title) {
		WSection section = new WSection(title);
		section.getContent().add(div);
		section.setMargin(new Margin(Size.XL));
		return section;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return "Corp Dir - " + getCurrentTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleStepError() {
		super.handleStepError();
		getMessages()
				.warn("A request was made that is not in the expected sequence and the application has been refreshed to its current state.");
	}

	/**
	 * @return the title of the current card
	 */
	private String getCurrentTitle() {
		return "Title";
//		return ((WSection) mgr.getVisible()).getDecoratedLabel().getText();
	}

	/**
	 * Retrieves the BrisApp ancestor of the given component.
	 *
	 * @param descendant the component to find the BrisApp ancestor of.
	 * @return the BrisApp ancestor for the given component, or null if not found.
	 */
	public static CorpDirApp getInstance(final WComponent descendant) {
		return WebUtilities.getAncestorOfClass(CorpDirApp.class, descendant);
	}
}
