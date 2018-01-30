package com.github.bordertech.flux.wc.view.smart.form;

import com.github.bordertech.flux.crud.actioncreator.CrudActionCreator;
import com.github.bordertech.flux.crud.store.CrudStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.mode.FormMode;
import com.github.bordertech.flux.wc.view.dumb.FormToolbarView;
import com.github.bordertech.flux.wc.view.dumb.FormView;
import com.github.bordertech.flux.wc.view.dumb.form.DefaultFormView;
import com.github.bordertech.flux.wc.view.dumb.toolbar.DefaultFormToolbarView;
import com.github.bordertech.flux.wc.view.event.util.FormEventUtil;
import com.github.bordertech.flux.wc.view.smart.CrudSmartView;
import com.github.bordertech.flux.wc.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Form View with a Toolbar View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class FormWithToolbarSmartView<S, K, T> extends DefaultMessageSmartView<T> implements CrudSmartView<S, K, T>, FormView<T> {

	private final FormView<T> formView;

	private final FormToolbarView toolbarView;

	public FormWithToolbarSmartView(final String viewId) {
		this(viewId, new DefaultFormView<T>("vw_entity"));
	}

	public FormWithToolbarSmartView(final String viewId, final FormView<T> formView) {
		this(viewId, formView, new DefaultFormToolbarView("vw_toolbar"));
	}

	public FormWithToolbarSmartView(final String viewId, final FormView<T> formView, final FormToolbarView toolbarView) {
		super(viewId, TemplateConstants.TEMPLATE_ENT_TOOLBAR);
		// Views
		this.formView = formView;
		this.toolbarView = toolbarView;

		addComponentToTemplate(TemplateConstants.TAG_VW_TOOLBAR, toolbarView);
		addComponentToTemplate(TemplateConstants.TAG_VW_FORM, formView);
	}

	@Override
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		// Handle the Form Events
		FormEventUtil.handleFormEvents(this, viewId, event, data);
	}

	@Override
	public String getActionCreatorKey() {
		return getComponentModel().entityCreatorKey;
	}

	@Override
	public void setActionCreatorKey(final String entityCreatorKey) {
		getOrCreateComponentModel().entityCreatorKey = entityCreatorKey;
	}

	@Override
	public CrudActionCreator<T> getActionCreatorByKey() {
		return StoreUtil.getActionCreator(getActionCreatorKey());
	}

	@Override
	public String getStoreKey() {
		return getComponentModel().entityStoreKey;
	}

	@Override
	public void setStoreKey(final String entityStoreKey) {
		getOrCreateComponentModel().entityStoreKey = entityStoreKey;
	}

	@Override
	public CrudStore<S, K, T> getStoreByKey() {
		return StoreUtil.getStore(getStoreKey());
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
