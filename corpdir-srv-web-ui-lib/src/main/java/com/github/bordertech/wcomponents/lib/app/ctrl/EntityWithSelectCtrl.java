package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;

/**
 * Controller for a CriterSelect and Entity View.
 *
 * @author jonathan
 * @param <T> the select entity
 */
public class EntityWithSelectCtrl<S, T> extends DefaultController {

	public EntityWithSelectCtrl(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public EntityWithSelectCtrl(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

		// Listeners
		// Select EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				T selected = (T) event.getData();
				handleSelectEvent(selected);
			}
		};
		registerCtrlListener(listener, ActionEventType.SELECT);

		// Loaded
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleLoadedOKEvent();
			}
		};
		registerCtrlListener(listener, ActionEventType.LOAD_OK);

		// Created
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleCreateOkEvent((T) event.getData());
			}
		};
		registerCtrlListener(listener, ActionEventType.CREATE_OK);
		// Updated
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleUpdateOkEvent((T) event.getData());
			}
		};
		registerCtrlListener(listener, ActionEventType.UPDATE_OK);
		// Deleted
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleDeleteOkEvent((T) event.getData());
			}
		};
		registerCtrlListener(listener, ActionEventType.DELETE_OK);

	}

	@Override
	protected void checkConfig() {
		super.checkConfig();
		if (getEntityView() == null) {
			throw new IllegalStateException("A entity view has not been set.");
		}
		if (getSelectView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
		if (getActionModel() == null) {
			throw new IllegalStateException("Entity action model has not been set.");
		}
	}

	@Override
	public void configViews() {
		super.configViews();
		getEntityView().setContentVisible(false);
	}

	public final EntityView<T> getEntityView() {
		return getComponentModel().entityView;
	}

	public final void setEntityView(final EntityView<T> entityView) {
		getOrCreateComponentModel().entityView = entityView;
		addView(entityView);
	}

	public final SelectView<T> getSelectView() {
		return getComponentModel().selectView;
	}

	public final void setSelectView(final SelectView<T> selectView) {
		getOrCreateComponentModel().selectView = selectView;
		addView(selectView);
	}

	public ActionModel<T> getActionModel() {
		return (ActionModel<T>) getModel(ActionModel.class);
	}

	protected void handleSelectEvent(final T selected) {
		// Reset Entity View
		EntityView<T> view = getEntityView();
		view.resetView();
		view.loadEntity(selected, EntityMode.VIEW);
	}

	protected void handleLoadedOKEvent() {
		getEntityView().setContentVisible(true);
	}

	protected void handleUpdateOkEvent(final T entity) {
		getSelectView().updateItem(entity);
	}

	protected void handleCreateOkEvent(final T entity) {
		getSelectView().addItem(entity);
	}

	protected void handleDeleteOkEvent(final T entity) {
		getSelectView().removeItem(entity);
	}

	@Override
	protected EntitySelectModel newComponentModel() {
		return new EntitySelectModel();
	}

	@Override
	protected EntitySelectModel getComponentModel() {
		return (EntitySelectModel) super.getComponentModel();
	}

	@Override
	protected EntitySelectModel getOrCreateComponentModel() {
		return (EntitySelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class EntitySelectModel<S, T> extends CtrlModel {

		private EntityView<T> entityView;

		private SelectView<T> selectView;
	}

}
