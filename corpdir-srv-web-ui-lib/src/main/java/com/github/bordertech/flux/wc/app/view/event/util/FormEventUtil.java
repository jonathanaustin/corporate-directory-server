package com.github.bordertech.flux.wc.app.view.event.util;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.FormOutcomeBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.smart.FormSmartView;

/**
 * Process form events.
 *
 * @author jonathan
 */
public class FormEventUtil {

	private FormEventUtil() {
	}

	public static <T> void handleFormBaseEvents(final FormSmartView<T> view, final FormBaseViewEvent type, final Object data) {
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

	public static <T> void handleFormOutcomeBaseEvents(final FormSmartView<T> view, final FormOutcomeBaseViewEvent type, final Object data) {

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
		view.getToolbarView().setFormMode(view.getFormView().getFormMode());
		view.getToolbarView().setFormReady(view.getFormView().isLoaded());
	}

	public static <T> void doLoad(final FormSmartView<T> view, final T entity, final FormMode mode) {
		view.resetFormViews();
		view.getFormView().loadEntity(entity, mode);
		view.getFormView().dispatchViewEvent(FormBaseViewEvent.ENTITY_MODE_CHANGED);
	}

	public static <T> void handleToolbarBaseEvents(final FormSmartView<T> view, final ToolbarBaseViewEvent type, final Object data) {

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
			T bean = view.getEntityCreator().createInstance();
			dispatchViewEvent(view, FormOutcomeBaseViewEvent.ADD_OK);
			dispatchViewEvent(view, FormBaseViewEvent.LOAD_NEW, bean);
		} catch (Exception e) {
			dispatchViewEvent(view, FormOutcomeBaseViewEvent.ADD_ERROR, e);
		}
	}

	public static <T> void handleToolbarCreateUpdateAction(final FormSmartView<T> view, final boolean create) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		// Do Validation
		if (!form.validateView()) {
			return;
		}
		// Do Save
		try {
			form.updateViewBean();
			T bean = form.getViewBean();
			if (create) {
				bean = view.getEntityCreator().create(bean);
			} else {
				bean = view.getEntityCreator().update(bean);
			}
			// Reload from the store
			bean = view.getEntityStore().retrieve(bean);
			view.resetFormViews();
			dispatchViewEvent(view, create ? FormOutcomeBaseViewEvent.CREATE_OK : FormOutcomeBaseViewEvent.UPDATE_OK, bean);
			dispatchViewEvent(view, FormBaseViewEvent.LOAD, bean);
		} catch (Exception e) {
			dispatchViewEvent(view, create ? FormOutcomeBaseViewEvent.CREATE_ERROR : FormOutcomeBaseViewEvent.UPDATE_ERROR, e);
		}
	}

	public static <T> void handleToolbarDeleteEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			view.getEntityCreator().delete(bean);
			view.resetFormViews();
			dispatchViewEvent(view, FormOutcomeBaseViewEvent.DELETE_OK);
		} catch (Exception e) {
			dispatchViewEvent(view, FormOutcomeBaseViewEvent.DELETE_ERROR, e);
		}
	}

	public static <T> void handleToolbarCancelEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		dispatchViewEvent(view, MessageBaseViewEvent.RESET);
		if (form.getFormMode() == FormMode.EDIT) {
			T bean = form.getViewBean();
			dispatchViewEvent(view, FormBaseViewEvent.LOAD, bean);
			return;
		}
		// Do a BACK
		dispatchViewEvent(view, ToolbarBaseViewEvent.BACK);
	}

	public static <T> void handleToolbarEditEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		form.setFormMode(FormMode.EDIT);
		dispatchViewEvent(view, FormBaseViewEvent.ENTITY_MODE_CHANGED);
	}

	public static <T> void handleToolbarRefreshEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			// Get Bean from the Store
			bean = view.getEntityStore().retrieve(bean);
			dispatchViewEvent(view, FormBaseViewEvent.LOAD, bean);
			dispatchViewEvent(view, FormOutcomeBaseViewEvent.REFRESH_OK);
		} catch (Exception e) {
			dispatchViewEvent(view, FormOutcomeBaseViewEvent.REFRESH_ERROR, e);
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
		view.dispatchViewEvent(MessageBaseViewEvent.ERROR, msg);
	}

	private static void dispatchSuccessMessage(final FormSmartView view, final String msg) {
		view.dispatchViewEvent(MessageBaseViewEvent.SUCCESS, msg);
	}

}
