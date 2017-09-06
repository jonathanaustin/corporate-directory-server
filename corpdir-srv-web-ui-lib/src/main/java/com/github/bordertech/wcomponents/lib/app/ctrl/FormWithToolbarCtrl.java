package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;

/**
 * Controller for a Form View and Form Toolbar View.
 *
 * @param <T> the entity type
 * @author jonathan
 */
public class FormWithToolbarCtrl<T> extends DefaultController {

	public FormWithToolbarCtrl() {
		this(null);
	}

	public FormWithToolbarCtrl(final String qualifier) {
		super(qualifier);
	}

	@Override
	public void setupListeners() {
		super.setupListeners();

		// Entity Action Event Listeners
		for (ActionEventType eventType : ActionEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleActionEvent(event);
				}
			};
			registerListener(listener, eventType);
		}
	}

	public final FormToolbarView getToolbarView() {
		return getComponentModel().toolbarView;
	}

	public final void setToolbarView(final FormToolbarView toolbarView) {
		getOrCreateComponentModel().toolbarView = toolbarView;
		addView(toolbarView);
	}

	public final FormView<T> getFormView() {
		return getComponentModel().formView;
	}

	public final void setFormView(final FormView<T> formView) {
		getOrCreateComponentModel().formView = formView;
		addView(formView);
	}

	public ActionModel<T> getActionModel() {
		return (ActionModel<T>) getModel("action");
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getToolbarView() == null) {
			throw new IllegalStateException("An toolbar view has not been set.");
		}
		if (getFormView() == null) {
			throw new IllegalStateException("A form view has not been set.");
		}
		if (getActionModel() == null) {
			throw new IllegalStateException("Entity service actions have not been set.");
		}
	}

	protected void handleActionEvent(final Event event) {
		ActionEventType type = (ActionEventType) event.getQualifier().getEventType();
		switch (type) {
			case BACK:
				handleBackAction();
				break;
			case CANCEL:
				handleCancelAction();
				break;
			case DELETE:
				handleDeleteAction();
				break;
			case EDIT:
				handleEditAction();
				break;
			case REFRESH:
				handleRefreshAction();
				break;
			case CREATE:
				handleSaveAction(true);
				break;
			case UPDATE:
				handleSaveAction(false);
				break;
			case ADD:
				handleAddAction();
				break;
			case LOAD_OK:
				handleLoadedOKAction();
				break;

			case ENTITY_MODE_CHANGED:
				handleEntityModeChanged();
				break;
		}
	}

	protected void handleBackAction() {
		resetViews();
	}

	protected void handleCancelAction() {
		FormView<T> view = getFormView();
		if (view.getFormMode() == FormMode.EDIT) {
			T bean = view.getViewBean();
			resetViews();
			doLoad(bean, FormMode.VIEW);
			return;
		}
		resetViews();
		// Do a BACK
		dispatchCtrlEvent(ActionEventType.BACK);
	}

	protected void handleEditAction() {
		FormView<T> view = getFormView();
		if (view.isLoaded()) {
			doEntityModeChange(FormMode.EDIT);
		}
	}

	protected void handleDeleteAction() {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		doEntityModeChange(FormMode.VIEW);
		T bean = view.getViewBean();
		try {
			doDelete(bean);
			resetViews();
			dispatchCtrlEvent(ActionEventType.DELETE_OK, bean);
			dispatchMessage(MsgEventType.SUCCESS, "Delete OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Delete failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.DELETE_ERROR, bean, e);
		}
	}

	protected void handleRefreshAction() {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		T bean = view.getViewBean();
		resetViews();
		try {
			bean = doRefresh(bean);
			dispatchCtrlEvent(ActionEventType.REFRESH_OK, bean);
			dispatchMessage(MsgEventType.SUCCESS, "Refreshed OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Refresh failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.REFRESH_ERROR, bean, e);
		}
	}

	protected void handleSaveAction(final boolean create) {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		// Do Validation
		if (!view.validateView()) {
			return;
		}
		// Do Save
		try {
			view.updateViewBean();
			T bean = view.getViewBean();
			if (create) {
				bean = doCreate(bean);
				resetViews();
				dispatchCtrlEvent(ActionEventType.CREATE_OK, bean);
			} else {
				bean = doUpdate(bean);
				resetViews();
				dispatchCtrlEvent(ActionEventType.UPDATE_OK, bean);
			}
			dispatchMessage(MsgEventType.SUCCESS, "Saved OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Save failed. " + e.getMessage());
			if (create) {
				dispatchCtrlEvent(ActionEventType.CREATE_ERROR, e);
			} else {
				dispatchCtrlEvent(ActionEventType.UPDATE_ERROR, e);
			}
		}
	}

	protected void handleAddAction() {
		resetViews();
		try {
			T bean = doCreateInstance();
			doLoad(bean, FormMode.ADD);
			doEntityModeChange(FormMode.ADD);
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "ADD failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.LOAD_ERROR, e);
		}
	}

	protected void handleLoadedOKAction() {
		getToolbarView().setContentVisible(true);
		getFormView().setContentVisible(true);
	}

	protected void handleEntityModeChanged() {
		// Keep Action View in SYNC
		FormView entityView = getFormView();
		FormToolbarView actionView = getToolbarView();
		actionView.setFormMode(entityView.getFormMode());
		actionView.setFormReady(entityView.isLoaded());
	}

	protected void doEntityModeChange(final FormMode mode) {
		getFormView().setFormMode(mode);
	}

	protected void doLoad(final T entity, final FormMode mode) {
		resetViews();
		getFormView().loadEntity(entity, mode);
	}

	protected T doCreate(final T entity) {
		return getActionModel().create(entity);
	}

	protected T doUpdate(final T entity) {
		return getActionModel().update(entity);
	}

	protected T doRefresh(final T entity) {
		return getActionModel().refresh(entity);
	}

	protected void doDelete(final T entity) {
		getActionModel().delete(entity);
	}

	protected T doCreateInstance() {
		return getActionModel().createInstance();
	}

	@Override
	protected FormToolbarModel<T> newComponentModel() {
		return new FormToolbarModel();
	}

	@Override
	protected FormToolbarModel<T> getComponentModel() {
		return (FormToolbarModel<T>) super.getComponentModel();
	}

	@Override
	protected FormToolbarModel<T> getOrCreateComponentModel() {
		return (FormToolbarModel<T>) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class FormToolbarModel<T> extends CtrlModel {

		private FormToolbarView toolbarView;

		private FormView<T> formView;
	}

}
