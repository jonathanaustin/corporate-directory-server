package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.FormCtrlEventType;
import com.github.bordertech.wcomponents.lib.app.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;

/**
 * Controller for a Form View and Form Toolbar View.
 *
 * @param <T> the entity type
 * @author jonathan
 */
public class FormAndToolbarCtrl<T> extends DefaultController implements ActionModelKey {

	@Override
	public void setupController() {
		super.setupController();

		// Form event type Listeners
		for (FormEventType eventType : FormEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleFormEvents(event);
				}
			};
			registerListener(listener, eventType);
		}

		// Toolbar event type Listeners
		for (ToolbarEventType eventType : ToolbarEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleToolbarEvents(event);
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

	@Override
	public void setActionModelKey(final String actionModelKey) {
		getOrCreateComponentModel().actionModelKey = actionModelKey;
	}

	@Override
	public String getActionModelKey() {
		return getComponentModel().actionModelKey;
	}

	protected ActionModel<T> getActionModelImpl() {
		return (ActionModel<T>) getModel(getActionModelKey());
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
		if (getActionModelKey() == null) {
			throw new IllegalStateException("No Action Model Key has been set.");
		}
	}

	protected void handleToolbarEvents(final Event event) {
		ToolbarEventType type = (ToolbarEventType) event.getQualifier().getEventType();
		switch (type) {
			case BACK:
				handleBackAction();
				break;
			case CANCEL:
				handleCancelAction();
				break;
			case EDIT:
				handleEditAction();
				break;
			case REFRESH:
				handleRefreshAction();
				break;
			case ADD:
				handleAddAction();
				break;
			case DELETE:
				handleDeleteAction();
				break;
			case CREATE:
				handleSaveAction(true);
				break;
			case UPDATE:
				handleSaveAction(false);
				break;

			case RESET_VIEW:
				// Let ResetCtrl do this
				break;
		}
	}

	protected void handleFormEvents(final Event event) {
		FormEventType type = (FormEventType) event.getQualifier().getEventType();
		switch (type) {
			case LOAD:
				doLoad((T) event.getData(), FormMode.VIEW);
				break;
			case LOAD_OK:
				handleLoadedOKEvent();
				break;
			case ENTITY_MODE_CHANGED:
				handleEntityModeChangedEvent();
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
		dispatchEvent(ToolbarEventType.BACK);
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
			dispatchEvent(FormCtrlEventType.DELETE_OK, bean);
			dispatchMessage(MsgEventType.SUCCESS, "Delete OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Delete failed. " + e.getMessage());
			dispatchEvent(FormCtrlEventType.DELETE_ERROR, bean, e);
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
			dispatchEvent(FormCtrlEventType.REFRESH_OK, bean);
			dispatchMessage(MsgEventType.SUCCESS, "Refreshed OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Refresh failed. " + e.getMessage());
			dispatchEvent(FormCtrlEventType.REFRESH_ERROR, bean, e);
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
				dispatchEvent(FormCtrlEventType.CREATE_OK, bean);
			} else {
				bean = doUpdate(bean);
				resetViews();
				dispatchEvent(FormCtrlEventType.UPDATE_OK, bean);
			}
			dispatchMessage(MsgEventType.SUCCESS, "Saved OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Save failed. " + e.getMessage());
			if (create) {
				dispatchEvent(FormCtrlEventType.CREATE_ERROR, e);
			} else {
				dispatchEvent(FormCtrlEventType.UPDATE_ERROR, e);
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
			dispatchEvent(FormCtrlEventType.LOAD_ERROR, e);
		}
	}

	protected void handleLoadedOKEvent() {
		getToolbarView().setContentVisible(true);
		getFormView().setContentVisible(true);
	}

	protected void handleEntityModeChangedEvent() {
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
		return getActionModelImpl().create(entity);
	}

	protected T doUpdate(final T entity) {
		return getActionModelImpl().update(entity);
	}

	protected T doRefresh(final T entity) {
		return getActionModelImpl().refresh(entity);
	}

	protected void doDelete(final T entity) {
		getActionModelImpl().delete(entity);
	}

	protected T doCreateInstance() {
		return getActionModelImpl().createInstance();
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

		private String actionModelKey;
	}

}
