package com.github.bordertech.flux.wc.app.view.event.util;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
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
			case SELECT:
				break;
		}
	}

	private static <T> void handleToolbarAddEvent(final FormSmartView<T> view) {
		view.resetFormViews();
		try {
			T bean = view.getEntityCreator().createInstance();
			dispatchViewEvent(view, FormBaseViewEvent.LOAD_NEW, bean);
		} catch (Exception e) {
			dispatchErrorMessage(view, "Add failed. " + e.getMessage());
		}
	}

	private static <T> void handleToolbarCreateUpdateAction(final FormSmartView<T> view, final boolean create) {
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
			dispatchViewEvent(view, FormBaseViewEvent.LOAD, bean);
			dispatchSuccessMessage(view, "Saved OK.");
		} catch (Exception e) {
			dispatchErrorMessage(view, "Save failed. " + e.getMessage());
		}
	}

	private static <T> void handleToolbarDeleteEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			view.getEntityCreator().delete(bean);
			view.resetFormViews();
			dispatchSuccessMessage(view, "Delete OK.");
		} catch (Exception e) {
			dispatchErrorMessage(view, "Delete failed. " + e.getMessage());
		}
	}

	private static <T> void handleToolbarCancelEvent(final FormSmartView<T> view) {
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

	private static <T> void handleToolbarEditEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		form.setFormMode(FormMode.EDIT);
		dispatchViewEvent(view, FormBaseViewEvent.ENTITY_MODE_CHANGED);
	}

	private static <T> void handleToolbarRefreshEvent(final FormSmartView<T> view) {
		FormView<T> form = view.getFormView();
		if (!form.isLoaded()) {
			return;
		}
		T bean = form.getViewBean();
		try {
			// Get Bean from the Store
			bean = view.getEntityStore().retrieve(bean);
			dispatchViewEvent(view, FormBaseViewEvent.LOAD, bean);
			dispatchSuccessMessage(view, "Refreshed OK.");
		} catch (Exception e) {
			dispatchErrorMessage(view, "Refresh failed. " + e.getMessage());
		}
	}

	private static void dispatchViewEvent(final FormSmartView view, final ViewEventType event) {
		view.dispatchViewEvent(event, null);
	}

	private static void dispatchViewEvent(final FormSmartView view, final ViewEventType event, final Object data) {
		view.dispatchViewEvent(event, data);
	}

	private static void dispatchErrorMessage(final FormSmartView view, final String msg) {
		view.dispatchViewEvent(MessageBaseViewEvent.ERROR, msg);
	}

	private static void dispatchSuccessMessage(final FormSmartView view, final String msg) {
		view.dispatchViewEvent(MessageBaseViewEvent.SUCCESS, msg);
	}

}
