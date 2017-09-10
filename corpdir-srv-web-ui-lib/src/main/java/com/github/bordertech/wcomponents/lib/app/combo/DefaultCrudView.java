package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.search.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.toolbar.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.form.DefaultFormView;
import com.github.bordertech.wcomponents.lib.app.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.toolbar.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.list.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListActionCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.model.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudView<S, T> extends DefaultMessageComboView<T> implements ActionModelKey, SearchModelKey {

	private final FormAndToolbarCtrl entityToolbarCtrl = new FormAndToolbarCtrl();
	private final PollingListCtrl<S, T> criteriaCtrl = new PollingListCtrl<>();

	public DefaultCrudView(final String title, final WComponent panel) {
		this(title, null, null, null, panel);
	}

	public DefaultCrudView(final String title, final SearchView<S> criteriaView2, final SelectView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super("wclib/hbs/layout/combo-ent-crud.hbs");

		// Setup Defaults
		SearchView criteriaView = criteriaView2 == null ? new CriteriaTextView() : criteriaView2;
		SelectView<T> selectView = selectView2 == null ? new SelectMenuView<T>() : selectView2;
		FormView<T> formView = formView2 == null ? new DefaultFormView<T>() : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Views
		PollingView<S, List<T>> pollingView = new DefaultPollingView<>();
		ToolbarView toolbarView = new DefaultToolbarView();
		FormToolbarView formToolbarView = new DefaultFormToolbarView();

		// Ctrls
		FormAndSelectCtrl<T> selectCtrl = new FormAndSelectCtrl<>();
		ResetViewCtrl resetCtrl = new ResetViewCtrl();
		ListActionCtrl listCtrl = new ListActionCtrl();

		// Set views on the Ctrls
		selectCtrl.setTargetView(formView);
		selectCtrl.setSelectView(selectView);
		entityToolbarCtrl.setToolbarView(formToolbarView);
		entityToolbarCtrl.setFormView(formView);
		criteriaCtrl.addView(criteriaView);
		criteriaCtrl.setPollingView(pollingView);
		listCtrl.setListView(selectView);

		// Add formToolbar to Select Ctrl (Controlled with the From)
		selectCtrl.addGroupFormView(formToolbarView);
		selectCtrl.addView(getMessageView());
		entityToolbarCtrl.addView(getMessageView());

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl1", selectCtrl);
		content.addTaggedComponent("vw-ctrl2", entityToolbarCtrl);
		content.addTaggedComponent("vw-ctrl3", criteriaCtrl);
		content.addTaggedComponent("vw-ctrl4", resetCtrl);
		content.addTaggedComponent("vw-ctrl5", listCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-toolbar-2", formToolbarView);
		content.addTaggedComponent("vw-crit", criteriaView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
		content.addTaggedComponent("vw-entity", formView);

		content.addParameter("vw-title", title);

		// Default visibility
		selectView.setContentVisible(false);
		formView.setContentVisible(false);
		formToolbarView.setContentVisible(false);

		setBlocking(true);
	}

	@Override
	public void setActionModelKey(final String key) {
		entityToolbarCtrl.setActionModelKey(key);
	}

	@Override
	public String getActionModelKey() {
		return entityToolbarCtrl.getActionModelKey();
	}

	@Override
	public void setSearchModelKey(final String key) {
		criteriaCtrl.setSearchModelKey(key);
	}

	@Override
	public String getSearchModelKey() {
		return criteriaCtrl.getSearchModelKey();
	}

}
