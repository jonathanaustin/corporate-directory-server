package com.github.bordertech.flux.wc.app.view.event.util;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseEventType;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseOutcomeEventType;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseEventType;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.flux.wc.app.view.smart.FormSmartView;

/**
 * Process form events.
 *
 * @author jonathan
 */
public class FormEventUtil {

	private FormEventUtil() {
	}

	public static <T> void handleFormEvents(final FormSmartView<T> view, final String viewId, final ViewEventType event, final Object data) {
		if (event instanceof FormBaseEventType) {
			handleFormBaseEvents(view, (FormBaseEventType) event, data);
		} else if (event instanceof FormBaseOutcomeEventType) {
			handleFormOutcomeBaseEvents(view, (FormBaseOutcomeEventType) event, data);
		} else if (event instanceof ToolbarBaseEventType) {
			handleToolbarBaseEvents(view, (ToolbarBaseEventType) event, data);
		}
	}

	public static <T> void handleFormBaseEvents(final FormSmartView<T> view, final FormBaseEventType type, final Object data) {
		switch (type) {
			case ENTITY_MODE_CHANGED:
				handleSyncToolbar(view);
				break;
			case LOAD:
				doLoad(view, (T) data, FormMode.VIEW);
				break;
			case LOAD_NEW:
				doLoad(view, (T) data, FormMode.ADD);
				break;
		}

	}

	public static <T> void handleFormOutcomeBaseEvents(final FormSmartView<T> view, final FormBaseOutcomeEventType type, final Object data) {

		// Always sync the toolbar
		handleSyncToolbar(view);

		switch (type) {

			// SUCCESS
			case ADD_OK:
			case LOAD_OK:
				break;
			case CREATE_OK:
			case UPDATE_OK:
				dispatchSuccessMessage(view, "Saved OK.");
				break;
			case DELETE_OK:
				dispatchSuccessMessage(view, "Deleted OK.");
				break;
			case REFRESH_OK:
				dispatchSuccessMessage(view, "Refreshed OK.");
				break;

			// ERROR
			case ADD_ERROR:
				dispatchErrorMessage(view, "Add failed.", data);
				break;
			case CREATE_ERROR:
			case UPDATE_ERROR:
				dispatchErrorMessage(view, "Save failed.", data);
				break;
			case LOAD_ERROR:
				dispatchErrorMessage(view, "Load failed.", data);
				break;
			case REFRESH_ERROR:
				dispatchErrorMessage(view, "Refresh failed.", data);
				break;
		}

	}

	public static <T> void handleSyncToolbar(final FormSmartView<T> view) {
		FormMode mode = view.getFormView().getFormMode();
		boolean loaded = view.getFormView().isLoaded();
		view.getFormToolbarView().setFormMode(mode);
		view.getFormToolbarView().setFormReady(loaded);
	}

	public static <T> void doLoad(final FormSmartView<T> view, final T entity, final FormMode mode) {
		view.resetFormViews();
		view.getFormView().loadEntity(entity, mode);
		view.getFormView().dispatchViewEvent(FormBaseEventType.ENTITY_MODE_CHANGED);
	}

	public static <T> void handleToolbarBaseEvents(final FormSmartView<T> view, final ToolbarBaseEventType type, final Object data) {

		switch (type) {
			// CRUD Actions
			case ADD:
				handleToolbarAddEvent(view);
				break;
			case CREATE:
				handleToolbarCreateUpdateAction(view, true);
				break;
			case UPDATE:
				handleToolbarCreateUpdateAction(view, false);
				break;
			case DELETE:
				handleToolbarDeleteEvent(view);
				break;
			case REFRESH:
				handleToolbarRefreshEvent(view);
				break;

			case CANCEL:
				handleToolbarCancelEvent(view);
				break;
			case EDIT:
				handleToolbarEditEvent(view);
				break;

			// Not Handled here
			case BACK:
			case RESET:
			case APPLY:
				break;
		}
	}

	public static <T> void handleToolbarAddEvent(final FormSmartView<T> view) {
		view.resetFormViews();
		try {
			T bean = view.getEntityActionCreator().createInstance();
			dispatchViewEvent(view, FormBaseOutcomeEventType.ADD_OK, bean);
		} catch (Exception e) {
			dispatchViewEvent(view, FormBaseOutcomeEventType.ADD_ERROR, e);
		}
	}

	public static <T> void handleToolbarCreateUpdateAction(final FormSmartView<T> view, final boolean create) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		// Do Validation (Will dispatch validation error messages view event)
		if (!form.validateView()) {
			return;
		}
		// Do Save
		try {
			form.updateViewBean();
			T bean = form.getViewBean();
			if (create) {
				bean = view.getEntityActionCreator().create(bean);
			} else {
				bean = view.getEntityActionCreator().update(bean);
			}
			// Reload from the store
			bean = view.getEntityStore().fetch(bean);
			view.resetFormViews();
			dispatchViewEvent(view, create ? FormBaseOutcomeEventType.CREATE_OK : FormBaseOutcomeEventType.UPDATE_OK, bean);
		} catch (Exception e) {
			dispatchViewEvent(view, create ? FormBaseOutcomeEventType.CREATE_ERROR : FormBaseOutcomeEventType.UPDATE_ERROR, e);
		}
	}

	public static <T> void handleToolbarDeleteEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			view.getEntityActionCreator().delete(bean);
			view.resetFormViews();
			dispatchViewEvent(view, FormBaseOutcomeEventType.DELETE_OK, bean);
		} catch (Exception e) {
			dispatchViewEvent(view, FormBaseOutcomeEventType.DELETE_ERROR, e);
		}
	}

	public static <T> void handleToolbarCancelEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		dispatchViewEvent(view, MessageBaseEventType.RESET);
		if (form.getFormMode() == FormMode.EDIT) {
			T bean = form.getViewBean();
			dispatchViewEvent(view, FormBaseEventType.LOAD, bean);
			return;
		}
		// Do a BACK
		view.resetFormViews();
		dispatchViewEvent(view, ToolbarBaseEventType.BACK);
	}

	public static <T> void handleToolbarEditEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		form.setFormMode(FormMode.EDIT);
		dispatchViewEvent(view, FormBaseEventType.ENTITY_MODE_CHANGED);
	}

	public static <T> void handleToolbarRefreshEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			// Get Bean from the Store
			bean = view.getEntityStore().fetch(bean);
			dispatchViewEvent(view, FormBaseOutcomeEventType.REFRESH_OK, bean);
		} catch (Exception e) {
			dispatchViewEvent(view, FormBaseOutcomeEventType.REFRESH_ERROR, e);
		}
	}

	private static void dispatchViewEvent(final FormSmartView view, final ViewEventType event) {
		view.dispatchViewEvent(event, null);
	}

	private static void dispatchViewEvent(final FormSmartView view, final ViewEventType event, final Object data) {
		view.dispatchViewEvent(event, data);
	}

	private static void dispatchErrorMessage(final FormSmartView view, final String msg, final Object data) {
		if (data instanceof Exception) {
			Exception excp = (Exception) data;
			dispatchErrorMessage(view, msg + " " + excp.getMessage());
		} else {
			dispatchErrorMessage(view, msg);
		}
	}

	private static void dispatchErrorMessage(final FormSmartView view, final String msg) {
		view.dispatchViewEvent(MessageBaseEventType.ERROR, msg);
	}

	private static void dispatchSuccessMessage(final FormSmartView view, final String msg) {
		view.dispatchViewEvent(MessageBaseEventType.SUCCESS, msg);
	}

}
