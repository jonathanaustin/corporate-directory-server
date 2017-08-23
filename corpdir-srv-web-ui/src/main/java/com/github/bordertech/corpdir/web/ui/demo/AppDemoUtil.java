package com.github.bordertech.corpdir.web.ui.demo;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.model.MyOrgUnitActionModel;
import com.github.bordertech.corpdir.web.ui.model.MyOrgUnitSearchModel;
import com.github.bordertech.corpdir.web.ui.view.BasicEntityPanel;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WSection;
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
import com.github.bordertech.wcomponents.lib.demo.model.MyStringSearchModel;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.ControllerEventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class AppDemoUtil {

	private AppDemoUtil() {
	}

	public static void configApplication(final WApplication app) {
	}

	public static WDiv buildDemo(final Dispatcher dispatcher) {
//		WView view1 = buildView1(dispatcher);
//		WView view2 = buildView2(dispatcher);
//		WView view3 = buildView3(dispatcher);
//		WView view4 = buildView4(dispatcher);
		View view5 = buildView5(dispatcher);

		//-----------
		// MAIN Controller
		Controller ctrl = new DefaultController(dispatcher);
//		ctrl.addView(view1);
//		ctrl.addView(view2);
//		ctrl.addView(view3);
//		ctrl.addView(view4);
		ctrl.addView(view5);
		ctrl.configViews();

		WButton button = new WButton("Reset");
		button.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatcher.dispatch(new Event(ControllerEventType.RESET));
			}
		});

		WDiv div = new WDiv();
		div.add(ctrl);
		div.add(button);
//		div.add(wrapInSection(view1, "View 1"));
//		div.add(wrapInSection(view2, "View 2"));
//		div.add(wrapInSection(view3, "View 3"));
//		div.add(wrapInSection(view4, "View 4"));
		div.add(wrapInSection(view5, "View 5"));

		return div;
	}

	public static View buildView1(final Dispatcher dispatcher) {
		ListWithCriteriaTextView<String> view = new ListWithCriteriaTextView<>(dispatcher, "A", new ListBasicView(dispatcher, "A"));
		// Set Model
		view.addModel(new MyStringSearchModel());
		return view;
	}

	public static View buildView2(final Dispatcher dispatcher) {
		EntityView<OrgUnit> entView = new DefaultEntityView<>(dispatcher, "B");
		EntityWithToolbarView<OrgUnit> view2 = new EntityWithToolbarView<>(dispatcher, "B", entView);
		entView.getEntityDetailsHolder().add(new BasicEntityPanel());
		// Set Model
		view2.addModel(new MyOrgUnitActionModel());
		return view2;
	}

	public static View buildView3(final Dispatcher dispatcher) {
		ListWithCriteriaTextView<OrgUnit> view3 = new ListWithCriteriaTextView<>(dispatcher, "X", new SelectMenuView(dispatcher, "X"));
		// Set Model
		view3.addModel(new MyOrgUnitSearchModel());
		return view3;
	}

	public static View buildView4(final Dispatcher dispatcher) {
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
		WContainer holder = view4.getContent();
		holder.add(ctrl4);
		holder.add(critView4);
		holder.add(pollingView4);
		holder.add(listView4);
		return view4;
	}

	public static View buildView5(final Dispatcher dispatcher) {
		// Entity View
		EntityWithToolbarView<OrgUnit> entView = new EntityWithToolbarView<>(dispatcher, "W");
		entView.getEntityView().getEntityDetailsHolder().add(new BasicEntityPanel());
		// Select View
		SelectWithCriteriaTextView<OrgUnit> select = new SelectWithCriteriaTextView<>(dispatcher, "W");
		EntityWithSelectView<String, OrgUnit> view = new EntityWithSelectView<>(dispatcher, "W", entView, select);

		// Set Models
		view.addModel(new MyOrgUnitActionModel());
		view.addModel(new MyOrgUnitSearchModel());
		view.makeBlocking();
		return view;
	}

	public static final WSection wrapInSection(final View view, final String title) {
		WSection section = new WSection(title);
		section.getContent().add(view);
		section.setMargin(new Margin(Size.XL));
		return section;
	}
}
