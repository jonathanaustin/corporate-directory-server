package com.github.bordertech.flux.wc.app.view.smart.crud;

import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.MessageView;
import com.github.bordertech.flux.wc.app.view.PollingView;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.ToolbarView;
import com.github.bordertech.flux.wc.app.view.form.AbstractFormView;
import com.github.bordertech.flux.wc.app.view.list.MenuSelectView;
import com.github.bordertech.flux.wc.app.view.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.app.view.polling.DefaultPollingView;
import com.github.bordertech.flux.wc.app.view.search.SearchTextView;
import com.github.bordertech.flux.wc.app.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.flux.wc.app.view.toolbar.DefaultFormToolbarView;
import com.github.bordertech.flux.wc.app.view.toolbar.DefaultToolbarView;
import com.github.bordertech.flux.wc.app.view.toolbar.ToolbarModelEventItem;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
//public class DefaultCrudView<S, T> extends DefaultMessageSmartView<T> implements ActionModelKey, SearchModelKey {
public class DefaultCrudView<S, T> extends DefaultMessageSmartView<T> {

//	private final FormToolbarCtrl formToolbarCtrl = new FormToolbarCtrl();
//	private final PollingSearchCtrl<S, T, C> pollingSearchCtrl = new PollingSearchCtrl();
//	private final PollingCollectionCtrl<S, T, C> pollingListCtrl = new PollingCollectionCtrl();
	private final SearchView searchView;
	private final SelectSingleView<T> selectView;
	private final FormView<T> formView;
	private final FormToolbarView formToolbarView = new DefaultFormToolbarView("vw-form-toolbar");

	public DefaultCrudView(final String viewId, final String title, final WComponent panel) {
		this(viewId, title, null, null, null, panel);
	}

	public DefaultCrudView(final String viewId, final String title, final WComponent panel, final SelectSingleView<T> selectView2) {
		this(viewId, title, null, selectView2, null, panel);
	}

	public DefaultCrudView(final String viewId, final String title, final SearchView<S> criteriaView2, final SelectSingleView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super(viewId, "wclib/hbs/layout/combo-ent-crud.hbs");

		// Setup Defaults
		searchView = criteriaView2 == null ? new SearchTextView("vw-crit") : criteriaView2;
		selectView = selectView2 == null ? (SelectSingleView<T>) new MenuSelectView<T>("vw-list") : selectView2;
		formView = formView2 == null ? new AbstractFormView<T>("vw-form") : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Views
		PollingView<List<T>> pollingView = new DefaultPollingView<>("vw-poll");
		ToolbarView toolbarView = new DefaultToolbarView("vw-toolbar-1");
		MessageView searchMessages = new DefaultMessageView("vw-crit-msg");

		// Form Messages
		MessageView formMessages = new DefaultMessageView("vw-form-msg");

		// Form Holder
		DefaultSmartView formCombo = new DefaultSmartView("vw-form", "wclib/hbs/layout/combo-ent-crud-form.hbs");
		formCombo.addHtmlClass("wc-panel-type-box");

		formCombo.addComponentToTemplate("vw-form-toolbar", formToolbarView);
		formCombo.addComponentToTemplate("vw-form-msg", formMessages);
		formCombo.addComponentToTemplate("vw-form-view", formView);

		addComponentToTemplate("vw-toolbar-1", toolbarView);
		addComponentToTemplate("vw-crit-msg", searchMessages);
		addComponentToTemplate("vw-crit", searchView);
		addComponentToTemplate("vw-poll", pollingView);
		addComponentToTemplate("vw-list", selectView);
		addComponentToTemplate("vw-form", formCombo);

		// Title
		getContent().addParameter("vw-title", title);

		// Toolbar Defaults
		toolbarView.addToolbarItem(ToolbarModelEventItem.ADD);

		// Default visibility
		selectView.setContentVisible(false);
		formCombo.setContentVisible(false);

//		setBlocking(true);
//		setQualifierAndMessageQualifierContext(true);
	}

	public final SelectSingleView<T> getSelectView() {
		return selectView;
	}

//	@Override
//	public void setActionModelKey(final String key) {
////		formToolbarCtrl.setActionModelKey(key);
//	}
//
//	@Override
//	public String getActionModelKey() {
//		return "";
////		return formToolbarCtrl.getActionModelKey();
//	}
//
//	@Override
//	public void setSearchModelKey(final String key) {
////		pollingListCtrl.setRetrieveCollectionModelKey(key);
//	}
//
//	@Override
//	public String getSearchModelKey() {
//		return "";
////		return pollingListCtrl.getRetrieveCollectionModelKey();
//	}
}
