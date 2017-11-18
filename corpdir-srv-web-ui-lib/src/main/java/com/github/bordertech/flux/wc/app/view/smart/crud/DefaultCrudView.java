package com.github.bordertech.flux.wc.app.view.smart.crud;

import com.github.bordertech.flux.app.actioncreator.ModifyEntityCreator;
import com.github.bordertech.flux.app.store.retrieve.RetrieveEntityStore;
import com.github.bordertech.flux.util.FluxUtil;
import com.github.bordertech.flux.view.DefaultSmartView;
import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.MessageView;
import com.github.bordertech.flux.wc.app.view.PollingView;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.ToolbarView;
import com.github.bordertech.flux.wc.app.view.dumb.form.DefaultFormView;
import com.github.bordertech.flux.wc.app.view.dumb.list.MenuSelectView;
import com.github.bordertech.flux.wc.app.view.dumb.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.app.view.dumb.polling.DefaultPollingView;
import com.github.bordertech.flux.wc.app.view.dumb.search.SearchTextView;
import com.github.bordertech.flux.wc.app.view.dumb.toolbar.DefaultFormToolbarView;
import com.github.bordertech.flux.wc.app.view.dumb.toolbar.DefaultToolbarView;
import com.github.bordertech.flux.wc.app.view.dumb.toolbar.ToolbarModifyItemType;
import com.github.bordertech.flux.wc.app.view.smart.FormSmartView;
import com.github.bordertech.flux.wc.app.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudView<S, T> extends DefaultMessageSmartView<T> implements FormSmartView<T> {

	private final SearchView searchView;
	private final SelectSingleView<T> selectView;
	private final PollingView<List<T>> pollingView = new DefaultPollingView<>("vw-poll");
	private final ToolbarView toolbarView = new DefaultToolbarView("vw-toolbar-1");
	private final MessageView searchMessages = new DefaultMessageView("vw-crit-msg");
	// Form Details
	private final DefaultSmartView formHolder = new DefaultSmartView("vw-form", "wclib/hbs/layout/combo-ent-crud-form.hbs");
	private final MessageView formMessages = new DefaultMessageView("vw-form-msg");
	private final FormToolbarView formToolbarView = new DefaultFormToolbarView("vw-form-toolbar");
	private final FormView<T> formView;

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
		selectView = selectView2 == null ? (SelectSingleView) new MenuSelectView("vw-list") : selectView2;
		formView = formView2 == null ? new DefaultFormView<T>("vw-form") : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Form Holder
		formHolder.addHtmlClass("wc-panel-type-box");
		formHolder.addComponentToTemplate("vw-form-toolbar", formToolbarView);
		formHolder.addComponentToTemplate("vw-form-msg", formMessages);
		formHolder.addComponentToTemplate("vw-form-view", formView);

		// Add views
		addComponentToTemplate("vw-toolbar-1", toolbarView);
		addComponentToTemplate("vw-crit-msg", searchMessages);
		addComponentToTemplate("vw-crit", searchView);
		addComponentToTemplate("vw-poll", pollingView);
		addComponentToTemplate("vw-list", selectView);
		addComponentToTemplate("vw-form", formHolder);

		// Title
		getContent().addParameter("vw-title", title);

		// Toolbar Defaults
		toolbarView.addToolbarItem(ToolbarModifyItemType.ADD);

		// Default visibility
		selectView.setContentVisible(false);
		formHolder.setContentVisible(false);

		setQualifierContext(true);
	}

	public final SelectSingleView<T> getSelectView() {
		return selectView;
	}

	@Override
	public String getEntityCreatorKey() {
		return getComponentModel().entityCreatorKey;
	}

	@Override
	public void setEntityCreatorKey(final String entityCreatorKey) {
		getOrCreateComponentModel().entityCreatorKey = entityCreatorKey;
	}

	@Override
	public ModifyEntityCreator<T> getEntityCreator() {
		return FluxUtil.getActionCreator(getEntityCreatorKey());
	}

	@Override
	public String getEntityStoreKey() {
		return getComponentModel().entityStoreKey;
	}

	@Override
	public void setEntityStoreKey(final String entityStoreKey) {
		getOrCreateComponentModel().entityStoreKey = entityStoreKey;
	}

	@Override
	public RetrieveEntityStore<T> getEntityStore() {
		return FluxUtil.getStore(getEntityStoreKey());
	}

	@Override
	public FormView<T> getFormView() {
		return formView;
	}

	@Override
	public FormToolbarView<T> getToolbarView() {
		return formToolbarView;
	}

	@Override
	public void resetFormViews() {
		formHolder.reset();
	}

	@Override
	protected SmartFormModel newComponentModel() {
		return new SmartFormModel();
	}

	@Override
	protected SmartFormModel getComponentModel() {
		return (SmartFormModel) super.getComponentModel();
	}

	@Override
	protected SmartFormModel getOrCreateComponentModel() {
		return (SmartFormModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class SmartFormModel extends SmartViewModel {

		private String entityStoreKey;

		private String entityCreatorKey;
	}

}
