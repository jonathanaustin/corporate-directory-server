package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.FormCtrlEventType;
import com.github.bordertech.wcomponents.lib.app.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		if (getTargetView() == null) {
			throw new IllegalStateException("A form view has not been set.");
		}
		if (getSelectView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
	}

	public final View<T> getTargetView() {
		return getComponentModel().targetView;
	}

	public final void setTargetView(final View<T> formView) {
		getOrCreateComponentModel().targetView = formView;
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
		resetFormView();
	}

	protected void handleLoadedOKEvent() {
		makeFormViewContentVisible(true);
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
		resetFormView();
	}

	protected void doLoadEntity(final T entity) {
		resetFormView();
		dispatchCtrlEvent(FormEventType.LOAD, entity);
	}

	protected void resetFormView() {
		getTargetView().resetView();
		for (View view : getGroupFormViews()) {
			view.resetView();
		}
	}

	protected void makeFormViewContentVisible(final boolean show) {
		getTargetView().setContentVisible(show);
		for (View view : getGroupFormViews()) {
			view.setContentVisible(show);
		}
	}

	public void addGroupFormView(final View view) {
		FormSelectModel model = getOrCreateComponentModel();
		if (model.formGroup == null) {
			model.formGroup = new ArrayList();
		}
		model.formGroup.add(view);
		addView(view);
	}

	public List<View> getGroupFormViews() {
		FormSelectModel model = getComponentModel();
		return model.formGroup == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(model.formGroup);
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

		private View<T> targetView;

		private SelectView<T> selectView;

		private List<View> formGroup;
	}

}
