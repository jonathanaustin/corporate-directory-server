package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.model.MyOrgUnitActionModel;
import com.github.bordertech.corpdir.web.ui.model.MyOrgUnitSearchModel;
import com.github.bordertech.corpdir.web.ui.model.MyStringSearchModel;
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
import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityView;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.ListBasicView;
import com.github.bordertech.wcomponents.lib.app.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.combo.EntityWithSelectView;
import com.github.bordertech.wcomponents.lib.app.combo.EntityWithToolbarView;
import com.github.bordertech.wcomponents.lib.app.combo.ListWithCriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.combo.SelectWithCriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.wc.DefaultDispatcher;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.ControllerEventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
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

	private final DefaultDispatcher dispatcher = new DefaultDispatcher();

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
				dispatcher.dispatch(new Event(ControllerEventType.RESET));
			}
		});

		//-----------
		// Build views;
//		WView view1 = buildView1();
//		WView view2 = buildView2();
//		WView view3 = buildView3();
//		WView view4 = buildView4();
		View view5 = buildView5();

		//-----------
		// MAIN Controller
		Controller ctrl = new DefaultController(dispatcher);
//		ctrl.addView(view1);
//		ctrl.addView(view2);
//		ctrl.addView(view3);
//		ctrl.addView(view4);
		ctrl.addView(view5);
		ctrl.configViews();

		WDiv div = new WDiv();
		div.add(ctrl);
		div.add(button);
//		div.add(wrapInSection((WDiv) view1, "View 1"));
//		div.add(wrapInSection((WDiv) view2, "View 2"));
//		div.add(wrapInSection((WDiv) view3, "View 3"));
//		div.add(wrapInSection((WDiv) view4, "View 4"));
		div.add(wrapInSection((WDiv) view5, "View 5"));
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

	private View buildView1() {
		ListWithCriteriaTextView<String> view = new ListWithCriteriaTextView<>(dispatcher, "A", new ListBasicView(dispatcher, "A"));
		// Set Model
		view.addModel(new MyStringSearchModel());
		return view;
	}

	private View buildView2() {
		EntityView<OrgUnit> entView = new DefaultEntityView<>(dispatcher, "B");
		EntityWithToolbarView<OrgUnit> view2 = new EntityWithToolbarView<>(dispatcher, "B", entView);
		entView.getContent().add(new BasicEntityPanel());
		// Set Model
		view2.addModel(new MyOrgUnitActionModel());
		return view2;
	}

	private View buildView3() {
		ListWithCriteriaTextView<OrgUnit> view3 = new ListWithCriteriaTextView<>(dispatcher, "X", new SelectMenuView(dispatcher, "X"));
		// Set Model
		view3.addModel(new MyOrgUnitSearchModel());
		return view3;
	}

	private View buildView4() {
		DefaultView view4 = new DefaultView(dispatcher, "Y");
		// Views
		CriteriaView<String> critView4 = new CriteriaTextView(dispatcher, "Y");
		ListView<OrgUnit> listView4 = new SelectMenuView(dispatcher, "Y");
		DefaultPollingView<String, List<OrgUnit>> pollingView4 = new DefaultPollingView<>(dispatcher, "Y");

		// Set views on Controller
		ListWithCriteriaCtrl<String, OrgUnit> ctrl4 = new ListWithCriteriaCtrl<>(dispatcher, "Y");
		ctrl4.setCriteriaView(critView4);
		ctrl4.setPollingView(pollingView4);
		ctrl4.setListView(listView4);
		// Set Model
		ctrl4.addModel(new MyOrgUnitSearchModel());

		// Add views to holder
		WDiv holder = view4.getContent();
		holder.add(ctrl4);
		holder.add(critView4);
		holder.add(pollingView4);
		holder.add(listView4);
		return view4;
	}

	private View buildView5() {
		// Entity View
		EntityWithToolbarView<OrgUnit> entView = new EntityWithToolbarView<>(dispatcher, "W");
		entView.getEntityView().getContent().add(new BasicEntityPanel());
		// Select View
		SelectWithCriteriaTextView<OrgUnit> select = new SelectWithCriteriaTextView<>(dispatcher, "W");
		EntityWithSelectView<String, OrgUnit> view = new EntityWithSelectView<>(dispatcher, "W", entView, select);

		// Set Models
		view.addModel(new MyOrgUnitActionModel());
		view.addModel(new MyOrgUnitSearchModel());
		view.makeBlocking();
		return view;
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
