package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.DefaultFormView;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.SearchModel;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 * @param <U> the model type
 */
public class DefaultCrudView<S, T, U extends ActionModel<T> & SearchModel<S, List<T>>> extends DefaultMessageComboView<T> {

	public DefaultCrudView(final String title, final U model, final WComponent panel) {
		this(title, model, null, null, null, panel);
	}

	public DefaultCrudView(final String title, final U model, final CriteriaView<S> criteriaView2, final SelectView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super("wclib/hbs/layout/combo-ent-crud.hbs");

		// Setup Defaults
		CriteriaView criteriaView = criteriaView2 == null ? new CriteriaTextView() : criteriaView2;
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
		FormAndToolbarCtrl entityToolbarCtrl = new FormAndToolbarCtrl<>();
		PollingListCtrl<S, T> criteriaCtrl = new PollingListCtrl<>();
		ResetViewCtrl resetCtrl = new ResetViewCtrl();

		// Set views on the Ctrls
		selectCtrl.setTargetView(formView);
		selectCtrl.setSelectView(selectView);
		entityToolbarCtrl.setToolbarView(formToolbarView);
		entityToolbarCtrl.setFormView(formView);
		criteriaCtrl.addView(criteriaView);
		criteriaCtrl.setPollingView(pollingView);
		criteriaCtrl.setListView(selectView);

		// Add formToolbar to Select Ctrl (Controlled with the From)
		selectCtrl.addGroupFormView(formToolbarView);
		selectCtrl.addView(getMessageView());
		entityToolbarCtrl.addView(getMessageView());

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl1", selectCtrl);
		content.addTaggedComponent("vw-ctrl2", entityToolbarCtrl);
		content.addTaggedComponent("vw-ctrl3", criteriaCtrl);
		content.addTaggedComponent("vw-ctrl4", resetCtrl);
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

//		String prefix = qualifier == null ? "" : qualifier;
//
//		selectView.addDispatcherOverride(prefix + "-1", MsgEventType.values());
//		selectCtrl.addListenerOverride(prefix + "-1", MsgEventType.values());
//
//		formView.addDispatcherOverride(prefix + "-2", MsgEventType.values());
//		entityToolbarCtrl.addListenerOverride(prefix + "-2", MsgEventType.values());
		// Set Models
		addModel("search", model);
		addModel("action", model);
		setBlocking(true);

	}

}
