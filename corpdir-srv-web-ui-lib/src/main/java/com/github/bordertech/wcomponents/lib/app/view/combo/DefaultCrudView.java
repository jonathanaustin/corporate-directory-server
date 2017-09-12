package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingSearchCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.model.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.form.AbstractFormView;
import com.github.bordertech.wcomponents.lib.app.view.list.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.ToolbarItem;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudView<S, T> extends DefaultMessageComboView<T> implements ActionModelKey, SearchModelKey {

	private final FormToolbarCtrl formToolbarCtrl = new FormToolbarCtrl();
	private final PollingSearchCtrl<S, T> pollingSearchCtrl = new PollingSearchCtrl();
	private final PollingListCtrl<S, T> pollingListCtrl = new PollingListCtrl();

	public DefaultCrudView(final String title, final WComponent panel) {
		this(title, null, null, null, panel);
	}

	public DefaultCrudView(final String title, final SearchView<S> criteriaView2, final SelectView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super("wclib/hbs/layout/combo-ent-crud.hbs");

		// Setup Defaults
		SearchView searchView = criteriaView2 == null ? new SearchTextView() : criteriaView2;
		SelectView<T> selectView = selectView2 == null ? new SelectMenuView<T>() : selectView2;
		FormView<T> formView = formView2 == null ? new AbstractFormView<T>() : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Views
		PollingView<S, List<T>> pollingView = new DefaultPollingView<>();
		ToolbarView toolbarView = new DefaultToolbarView();
		FormToolbarView formToolbarView = new DefaultFormToolbarView();

		// Ctrls
		FormSelectCtrl<T> formSelectCtrl = new FormSelectCtrl<>();
		FormMainCtrl formCtrl = new FormMainCtrl();
		ResetViewCtrl resetCtrl = new ResetViewCtrl();
		ListMainCtrl listCtrl = new ListMainCtrl();

		// Set views on the Ctrls
		formSelectCtrl.setSelectView(selectView);
		formCtrl.setFormView(formView);
		formToolbarCtrl.setToolbarView(formToolbarView);
		formToolbarCtrl.setFormView(formView);
		pollingSearchCtrl.setPollingView(pollingView);
		pollingListCtrl.setPollingView(pollingView);
		listCtrl.setListView(selectView);

		// Setup a VIEW as a Section to holf the Toolbar/Edit
		DefaultComboView editView = new DefaultComboView();
		WSection editSection = new WSection("Details");
		editSection.getContent().add(formToolbarView);
		editSection.getContent().add(formView);
		editView.getContent().addTaggedComponent("vw-content", editSection);

		// Add EditView to Select Ctrl (Controlled with the From)
		formCtrl.addGroupFormView(editView);
		formCtrl.addGroupFormView(formToolbarView);

		// Add message view to the controllers
		formSelectCtrl.addView(getMessageView());
		formToolbarCtrl.addView(getMessageView());

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl1", formSelectCtrl);
		content.addTaggedComponent("vw-ctrl2", formToolbarCtrl);
		content.addTaggedComponent("vw-ctrl3", pollingSearchCtrl);
		content.addTaggedComponent("vw-ctrl4", resetCtrl);
		content.addTaggedComponent("vw-ctrl5", listCtrl);
		content.addTaggedComponent("vw-ctrl6", formCtrl);
		content.addTaggedComponent("vw-ctrl7", pollingListCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-crit", searchView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
		content.addTaggedComponent("vw-entity", editView);

		content.addParameter("vw-title", title);

		// Toolbar Defaults
		toolbarView.addToolbarType(ToolbarItem.ADD);

		// Default visibility
		selectView.setContentVisible(false);
		editView.setContentVisible(false);
		formView.setContentVisible(false);
		formToolbarView.setContentVisible(false);

		// Margins
		setBlocking(true);
	}

	@Override
	public void setActionModelKey(final String key) {
		formToolbarCtrl.setActionModelKey(key);
	}

	@Override
	public String getActionModelKey() {
		return formToolbarCtrl.getActionModelKey();
	}

	@Override
	public void setSearchModelKey(final String key) {
		pollingListCtrl.setSearchModelKey(key);
	}

	@Override
	public String getSearchModelKey() {
		return pollingListCtrl.getSearchModelKey();
	}

}
