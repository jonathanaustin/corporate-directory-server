package com.github.bordertech.flux.wc.app.view.smart.form;

import com.github.bordertech.flux.crud.actioncreator.EntityActionCreator;
import com.github.bordertech.flux.crud.store.retrieve.EntityStore;
import com.github.bordertech.flux.util.FluxUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.dumb.form.DefaultFormView;
import com.github.bordertech.flux.wc.app.view.dumb.toolbar.DefaultFormToolbarView;
import com.github.bordertech.flux.wc.app.view.event.util.FormEventUtil;
import com.github.bordertech.flux.wc.app.view.smart.FormSmartView;
import com.github.bordertech.flux.wc.app.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Form View with a Toolbar View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class FormWithToolbarSmartView<T> extends DefaultMessageSmartView<T> implements FormSmartView<T>, FormView<T> {

	private final FormView<T> formView;

	private final FormToolbarView toolbarView;

	public FormWithToolbarSmartView(final String viewId) {
		this(viewId, new DefaultFormView<T>("vw_entity"));
	}

	public FormWithToolbarSmartView(final String viewId, final FormView<T> formView) {
		this(viewId, formView, new DefaultFormToolbarView("vw_toolbar"));
	}

	public FormWithToolbarSmartView(final String viewId, final FormView<T> formView, final FormToolbarView toolbarView) {
		super(viewId, "wclib/hbs/layout/combo-ent-toolbar.hbs");
		// Views
		this.formView = formView;
		this.toolbarView = toolbarView;

		addComponentToTemplate("vw-toolbar", toolbarView);
		addComponentToTemplate("vw-form", formView);
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		// Handle the Form Events
		FormEventUtil.handleFormEvents(this, viewId, event, data);
	}

	@Override
	public String getEntityActionCreatorKey() {
		return getComponentModel().entityCreatorKey;
	}

	@Override
	public void setEntityActionCreatorKey(final String entityCreatorKey) {
		getOrCreateComponentModel().entityCreatorKey = entityCreatorKey;
	}

	@Override
	public EntityActionCreator<T> getEntityActionCreator() {
		return FluxUtil.getActionCreator(getEntityActionCreatorKey());
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
	public EntityStore<T> getEntityStore() {
		return FluxUtil.getStore(getEntityStoreKey());
	}

	@Override
	public final FormView<T> getFormView() {
		return formView;
	}

	@Override
	public final FormToolbarView getFormToolbarView() {
		return toolbarView;
	}

	@Override
	public void resetFormViews() {
		reset();
	}

	@Override
	public FormMode getFormMode() {
		return formView.getFormMode();
	}

	@Override
	public void setFormMode(final FormMode mode) {
		formView.setFormMode(mode);
	}

	@Override
	public boolean isFormReadonly() {
		return formView.isFormReadonly();
	}

	@Override
	public boolean isLoaded() {
		return formView.isLoaded();
	}

	@Override
	public void loadEntity(final T entity, final FormMode mode) {
		formView.loadEntity(entity, mode);
	}

	@Override
	public T getViewBean() {
		return formView.getViewBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		formView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		formView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return formView.validateView();
	}

	@Override
	public WContainer getFormHolder() {
		return formView.getFormHolder();
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
