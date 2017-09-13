package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.app.event.ModelOutcomeEventType;
import com.github.bordertech.wcomponents.lib.app.event.NavigationEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;

/**
 * Controller for a Form View and Form Toolbar View.
 *
 * @param <T> the entity type
 * @author jonathan
 */
public class FormToolbarCtrl<T> extends DefaultController implements ActionModelKey {

	@Override
	public void setupController() {
		super.setupController();
		// Navigation event type Listeners
		for (NavigationEventType eventType : NavigationEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleNavigationEvents(event);
				}
			};
			registerListener(eventType, listener);
		}

		// Model event type Listeners
		for (ModelEventType eventType : ModelEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleModelEvents(event);
				}
			};
			registerListener(eventType, listener);
		}

		// MODE CHANGED
		registerListener(FormEventType.ENTITY_MODE_CHANGED, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSyncToolbar();
			}
		});

		// LOADED OK
		registerListener(FormEventType.LOAD_OK, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSyncToolbar();
			}
		});

		// RESET FORM
		registerListener(FormEventType.RESET_FORM, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				resetViews();
			}
		});

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

	protected void handleNavigationEvents(final Event event) {
		NavigationEventType type = (NavigationEventType) event.getQualifier().getEventType();
		switch (type) {
			case BACK:
				dispatchEvent(FormEventType.RESET_FORM);
				break;
			case RESET_VIEW:
				handleResetEvent();
				break;
		}
	}

	protected void handleResetEvent() {
		ComboView view = findParentCombo();
		if (view != null) {
			view.resetView();
		}
	}

	protected void handleModelEvents(final Event event) {
		ModelEventType type = (ModelEventType) event.getQualifier().getEventType();
		switch (type) {
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

			case SELECTED:
				break;
		}
	}

	protected void handleCancelAction() {
		FormView<T> view = getFormView();
		if (view.getFormMode() == FormMode.EDIT) {
			T bean = view.getViewBean();
			dispatchEvent(FormEventType.LOAD, bean);
			return;
		}
		// Do a BACK
		dispatchEvent(NavigationEventType.BACK);
	}

	protected void handleEditAction() {
		FormView<T> view = getFormView();
		if (view.isLoaded()) {
			getFormView().setFormMode(FormMode.EDIT);
			dispatchEvent(FormEventType.ENTITY_MODE_CHANGED);
		}
	}

	protected void handleDeleteAction() {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		T bean = view.getViewBean();
		try {
			doDelete(bean);
			dispatchEvent(FormEventType.RESET_FORM);
			dispatchEvent(ModelOutcomeEventType.DELETE_OK, bean);
			dispatchMessage(MsgEventType.SUCCESS, "Delete OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Delete failed. " + e.getMessage());
			dispatchEvent(ModelOutcomeEventType.DELETE_ERROR, bean, e);
		}
	}

	protected void handleRefreshAction() {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		T bean = view.getViewBean();
		try {
			bean = doRefresh(bean);
			dispatchEvent(FormEventType.LOAD, bean);
			dispatchEvent(ModelOutcomeEventType.REFRESH_OK, bean);
			dispatchMessage(MsgEventType.SUCCESS, "Refreshed OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Refresh failed. " + e.getMessage());
			dispatchEvent(ModelOutcomeEventType.REFRESH_ERROR, bean, e);
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
				dispatchEvent(FormEventType.RESET_FORM);
				dispatchEvent(ModelOutcomeEventType.CREATE_OK, bean);
			} else {
				bean = doUpdate(bean);
				dispatchEvent(FormEventType.RESET_FORM);
				dispatchEvent(ModelOutcomeEventType.UPDATE_OK, bean);
			}
			dispatchMessage(MsgEventType.SUCCESS, "Saved OK.");
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "Save failed. " + e.getMessage());
			if (create) {
				dispatchEvent(ModelOutcomeEventType.CREATE_ERROR, e);
			} else {
				dispatchEvent(ModelOutcomeEventType.UPDATE_ERROR, e);
			}
		}
	}

	protected void handleAddAction() {
		resetViews();
		try {
			T bean = doCreateInstance();
			dispatchEvent(FormEventType.LOAD_NEW, bean);
		} catch (Exception e) {
			dispatchMessage(MsgEventType.ERROR, "ADD failed. " + e.getMessage());
			dispatchEvent(FormEventType.LOAD_ERROR, e);
		}
	}

	protected void handleSyncToolbar() {
		getToolbarView().setFormMode(getFormView().getFormMode());
		getToolbarView().setFormReady(getFormView().isLoaded());
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
