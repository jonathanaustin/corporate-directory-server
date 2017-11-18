package com.github.bordertech.flux.wc.app.view.smart.form;

import com.github.bordertech.flux.app.actioncreator.ModifyEntityCreator;
import com.github.bordertech.flux.app.store.retrieve.RetrieveEntityStore;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.dumb.form.DefaultFormView;
import com.github.bordertech.flux.wc.app.view.dumb.toolbar.DefaultFormToolbarView;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.FormOutcomeBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
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

	private final ModifyEntityCreator<T> creator;

	private final RetrieveEntityStore<T> store;

	private final FormView<T> formView;

	private final FormToolbarView toolbarView;

	public FormWithToolbarSmartView(final String viewId, final ModifyEntityCreator<T> creator, final RetrieveEntityStore<T> store) {
		this(viewId, creator, store, new DefaultFormView<T>("vw-entity"));
	}

	public FormWithToolbarSmartView(final String viewId, final ModifyEntityCreator<T> creator, final RetrieveEntityStore<T> store, final FormView<T> formView) {
		this(viewId, creator, store, formView, new DefaultFormToolbarView("vw-toolbar"));
	}

	public FormWithToolbarSmartView(final String viewId, final ModifyEntityCreator<T> creator, final RetrieveEntityStore<T> store, final FormView<T> formView, final FormToolbarView toolbarView) {
		super(viewId, "wclib/hbs/layout/combo-ent-toolbar.hbs");

		this.creator = creator;
		this.store = store;

		// Views
		this.formView = formView;
		this.toolbarView = toolbarView;

		addComponentToTemplate("vw-toolbar", toolbarView);
		addComponentToTemplate("vw-form", formView);
	}

	@Override
	public ModifyEntityCreator<T> getEntityCreator() {
		return creator;
	}

	@Override
	public RetrieveEntityStore<T> getEntityStore() {
		return store;
	}

	@Override
	public final FormView<T> getFormView() {
		return formView;
	}

	@Override
	public final FormToolbarView getToolbarView() {
		return toolbarView;
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof FormBaseViewEvent) {
			handleFormBaseEvents((FormBaseViewEvent) event, data);
		} else if (event instanceof FormOutcomeBaseViewEvent) {
			handleFormOutcomeBaseEvents((FormOutcomeBaseViewEvent) event, data);
		} else if (event instanceof ToolbarBaseViewEvent) {
			handleToolbarBaseEvents((ToolbarBaseViewEvent) event, data);
		}
	}

	@Override
	public void resetFormViews() {
		reset();
	}

	protected void handleFormBaseEvents(final FormBaseViewEvent type, final Object data) {
		FormEventUtil.handleFormBaseEvents(this, type, data);
	}

	protected void handleFormOutcomeBaseEvents(final FormOutcomeBaseViewEvent type, final Object data) {
		FormEventUtil.handleFormOutcomeBaseEvents(this, type, data);
	}

	protected void handleToolbarBaseEvents(final ToolbarBaseViewEvent type, final Object data) {
		FormEventUtil.handleToolbarBaseEvents(this, type, data);
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
}
