package com.github.bordertech.flux.wc.app.view.event.util;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.smart.FormSmartView;

/**
 * Process message events.
 *
 * @author jonathan
 */
public class FormEventUtil {

	private FormEventUtil() {
	}

	public static <T> void handleSyncToolbar(final FormToolbarView<T> toolbar, final FormView<T> form) {
		toolbar.setFormMode(form.getFormMode());
		toolbar.setFormReady(form.isLoaded());
	}

	public static <T> void doLoad(final FormView<T> form, final T entity, final FormMode mode) {
		form.resetView();
		form.loadEntity(entity, mode);
		form.dispatchViewEvent(FormBaseViewEvent.ENTITY_MODE_CHANGED);
	}

	public static <T> void handleToolbarBaseEvents(final FormSmartView<T> form, final ToolbarBaseViewEvent type, final Object data) {

		switch (type) {
			// CRUD Actions
			case ADD:
				handleToolbarAddEvent(form);
				break;
			case CREATE:
				handleToolbarCreateUpdateAction(form, true);
				break;
			case UPDATE:
				handleToolbarCreateUpdateAction(form, false);
				break;
			case DELETE:
				handleToolbarDeleteEvent(form);
				break;
			case REFRESH:
				handleToolbarRefreshEvent(form);
				break;

			case CANCEL:
				handleToolbarCancelEvent(form);
				break;
			case EDIT:
				handleToolbarEditEvent(form);
				break;

			// Not Handled here
			case BACK:
			case RESET:
			case SELECT:
				break;
		}
	}

	private static <T> void handleToolbarAddEvent(final FormSmartView<T> form) {
		form.resetView();
		try {
			T bean = form.getEntityCreator().createInstance();
			dispatchViewEvent(form, FormBaseViewEvent.LOAD_NEW, bean);
		} catch (Exception e) {
			dispatchErrorMessage(form, "Add failed. " + e.getMessage());
		}
	}

	private static <T> void handleToolbarCreateUpdateAction(final FormSmartView<T> form, final boolean create) {
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
				bean = form.getEntityCreator().create(bean);
			} else {
				bean = form.getEntityCreator().update(bean);
			}
			// Reload from the store
			bean = form.getEntityStore().retrieve(bean);
			form.resetView();
			dispatchViewEvent(form, FormBaseViewEvent.LOAD, bean);
			dispatchSuccessMessage(form, "Saved OK.");
		} catch (Exception e) {
			dispatchErrorMessage(form, "Save failed. " + e.getMessage());
		}
	}

	private static <T> void handleToolbarDeleteEvent(final FormSmartView<T> form) {
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			form.getEntityCreator().delete(bean);
			form.resetView();
			dispatchSuccessMessage(form, "Delete OK.");
		} catch (Exception e) {
			dispatchErrorMessage(form, "Delete failed. " + e.getMessage());
		}
	}

	private static <T> void handleToolbarCancelEvent(final FormSmartView<T> form) {
		dispatchViewEvent(form, MessageBaseViewEvent.RESET);
		if (form.getFormMode() == FormMode.EDIT) {
			T bean = form.getViewBean();
			dispatchViewEvent(form, FormBaseViewEvent.LOAD, bean);
			return;
		}
		// Do a BACK
		dispatchViewEvent(form, ToolbarBaseViewEvent.BACK);
	}

	private static <T> void handleToolbarEditEvent(final FormSmartView<T> form) {
		if (!form.isLoaded()) {
			return;
		}
		form.setFormMode(FormMode.EDIT);
		dispatchViewEvent(form, FormBaseViewEvent.ENTITY_MODE_CHANGED);
	}

	private static <T> void handleToolbarRefreshEvent(final FormSmartView<T> form) {
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			// Get Bean from the Store
			bean = form.getEntityStore().retrieve(bean);
			dispatchViewEvent(form, FormBaseViewEvent.LOAD, bean);
			dispatchSuccessMessage(form, "Refreshed OK.");
		} catch (Exception e) {
			dispatchErrorMessage(form, "Refresh failed. " + e.getMessage());
		}
	}

	private static void dispatchViewEvent(final FormSmartView form, final ViewEventType event) {
		form.dispatchViewEvent(event, null);
	}

	private static void dispatchViewEvent(final FormSmartView form, final ViewEventType event, final Object data) {
		form.dispatchViewEvent(event, data);
	}

	private static void dispatchErrorMessage(final FormSmartView form, final String msg) {
		form.dispatchViewEvent(MessageBaseViewEvent.ERROR, msg);
	}

	private static void dispatchSuccessMessage(final FormSmartView form, final String msg) {
		form.dispatchViewEvent(MessageBaseViewEvent.SUCCESS, msg);
	}

}
