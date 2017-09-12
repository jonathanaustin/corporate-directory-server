package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormActionCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListActionCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.SearchPollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.model.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.bar.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.bar.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.bar.ToolbarItem;
import com.github.bordertech.wcomponents.lib.app.view.form.AbstractFormView;
import com.github.bordertech.wcomponents.lib.app.view.list.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
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

	private final FormAndToolbarCtrl formToolbarCtrl = new FormAndToolbarCtrl();
	private final SearchPollingListCtrl<S, T> searchCtrl = new SearchPollingListCtrl<>();

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
		FormAndSelectCtrl<T> formSelectCtrl = new FormAndSelectCtrl<>();
		FormActionCtrl formCtrl = new FormActionCtrl();
		ResetViewCtrl resetCtrl = new ResetViewCtrl();
		ListActionCtrl listCtrl = new ListActionCtrl();

		// Set views on the Ctrls
		formSelectCtrl.setSelectView(selectView);
		formCtrl.setFormView(formView);
		formToolbarCtrl.setToolbarView(formToolbarView);
		formToolbarCtrl.setFormView(formView);
		searchCtrl.setPollingView(pollingView);
		listCtrl.setListView(selectView);

		// Setup a VIEW as a Section to holf the Toolbar/Edit
		DefaultComboView editView = new DefaultComboView();
		WSection editSection = new WSection("Details");
//		editSection.setMargin(new Margin(Size.XL));
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
		content.addTaggedComponent("vw-ctrl3", searchCtrl);
		content.addTaggedComponent("vw-ctrl4", resetCtrl);
		content.addTaggedComponent("vw-ctrl5", listCtrl);
		content.addTaggedComponent("vw-ctrl6", formCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-crit", searchView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
//		content.addTaggedComponent("vw-toolbar-2", formToolbarView);
//		content.addTaggedComponent("vw-entity", formView);
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
//		pollingView.addHtmlClass("wc-margin-n-lg");
//		selectView.addHtmlClass("wc-margin-n-lg");
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
		searchCtrl.setSearchModelKey(key);
	}

	@Override
	public String getSearchModelKey() {
		return searchCtrl.getSearchModelKey();
	}

}
