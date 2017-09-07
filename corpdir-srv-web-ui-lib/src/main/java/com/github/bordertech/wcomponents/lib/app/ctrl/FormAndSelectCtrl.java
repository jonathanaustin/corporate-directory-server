package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.FormCtrlEventType;
import com.github.bordertech.wcomponents.lib.app.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;

/**
 * Controller for a Form View and Select View.
 *
 * @author jonathan
 * @param <T> the select entity
 */
public class FormAndSelectCtrl<T> extends DefaultController {

	public FormAndSelectCtrl() {
		this(null);
	}

	public FormAndSelectCtrl(final String qualifier) {
		super(qualifier);
	}

	@Override
	public void setupListeners() {
		super.setupListeners();
		// Listeners

		// Form event type Listeners
		for (FormCtrlEventType eventType : FormCtrlEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleFormCtrlEvents(event);
				}
			};
			registerListener(listener, eventType);
		}

		// ADD EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleAddEvent();
			}
		};
		registerListener(listener, ToolbarEventType.ADD);
		// BACK
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleBackEvent();
			}
		};
		registerListener(listener, ToolbarEventType.BACK);

		// Select EVENT
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				T selected = (T) event.getData();
				handleSelectEvent(selected);
			}
		};
		registerListener(listener, ListEventType.SELECT);

		// LIST Reset
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleListResetEvent();
			}
		};
		registerListener(listener, ListEventType.RESET_LIST);

		// Loaded
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleLoadedOKEvent();
			}
		};
		registerListener(listener, FormEventType.LOAD_OK);

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getFormView() == null) {
			throw new IllegalStateException("A form view has not been set.");
		}
		if (getSelectView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
	}

	public final FormView<T> getFormView() {
		return getComponentModel().formView;
	}

	public final void setFormView(final FormView<T> formView) {
		getOrCreateComponentModel().formView = formView;
		addView(formView);
	}

	public final SelectView<T> getSelectView() {
		return getComponentModel().selectView;
	}

	public final void setSelectView(final SelectView<T> selectView) {
		getOrCreateComponentModel().selectView = selectView;
		addView(selectView);
	}

	protected void handleFormCtrlEvents(final Event event) {
		FormCtrlEventType type = (FormCtrlEventType) event.getQualifier().getEventType();
		switch (type) {
			case CREATE_OK:
				handleCreateOkEvent((T) event.getData());
				break;
			case CREATE_ERROR:
				break;
			case DELETE_OK:
				handleDeleteOkEvent((T) event.getData());
				break;
			case UPDATE_OK:
				handleUpdateOkEvent((T) event.getData());
				break;

			case DELETE_ERROR:
			case LOAD_ERROR:
			case REFRESH_ERROR:
			case REFRESH_OK:
			case UPDATE_ERROR:
				break;
		}

	}

	protected void handleAddEvent() {
		getSelectView().clearSelectedIdx();
	}

	protected void handleSelectEvent(final T selected) {
		// Reset Entity View
		doLoadEntity(selected);
	}

	protected void handleListResetEvent() {
		getFormView().resetView();
	}

	protected void handleBackEvent() {
		getFormView().resetView();
	}

	protected void handleLoadedOKEvent() {
		getFormView().setContentVisible(true);
	}

	protected void handleUpdateOkEvent(final T entity) {
		getSelectView().updateItem(entity);
		// Reload Entity
		doLoadEntity(entity);
	}

	protected void handleCreateOkEvent(final T entity) {
		getSelectView().addItem(entity);
		getSelectView().showList(true);
		getSelectView().setSelected(entity);
		// Reload Entity
		doLoadEntity(entity);
	}

	protected void handleDeleteOkEvent(final T entity) {
		getSelectView().removeItem(entity);
		if (getSelectView().getViewBean().isEmpty()) {
			getSelectView().showList(false);
		}
		getFormView().resetView();
	}

	protected void doLoadEntity(final T entity) {
		FormView<T> view = getFormView();
		view.resetView();
		view.loadEntity(entity, FormMode.VIEW);
	}

	@Override
	protected FormSelectModel newComponentModel() {
		return new FormSelectModel();
	}

	@Override
	protected FormSelectModel getComponentModel() {
		return (FormSelectModel) super.getComponentModel();
	}

	@Override
	protected FormSelectModel getOrCreateComponentModel() {
		return (FormSelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class FormSelectModel<T> extends CtrlModel {

		private FormView<T> formView;

		private SelectView<T> selectView;
	}

}
