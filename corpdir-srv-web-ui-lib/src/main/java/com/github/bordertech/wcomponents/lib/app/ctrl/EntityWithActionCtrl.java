package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.flux.impl.ExecuteService;
import com.github.bordertech.wcomponents.lib.flux.impl.WView;

/**
 * Controller for an Entity View and Entity Action view.
 *
 * @param <T> the entity type
 * @author jonathan
 */
public class EntityWithActionCtrl<T> extends DefaultController {

	public EntityWithActionCtrl(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public EntityWithActionCtrl(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

		// Entity Action Event Listeners
		for (ActionEventType eventType : ActionEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleEntityActionEvent(event);
				}
			};
			registerCtrlListener(listener, eventType);
		}

		// TODO Add Action Error Listeners
	}

	public final EntityActionView getEntityActionView() {
		return getComponentModel().entityActionView;
	}

	public final void setEntityActionView(final EntityActionView actionView) {
		getOrCreateComponentModel().entityActionView = actionView;
		actionView.setController(this);
		addView(actionView);
	}

	public final EntityView<T> getEntityView() {
		return getComponentModel().entityView;
	}

	public final void setEntityView(final EntityView<T> entityView) {
		getOrCreateComponentModel().entityView = entityView;
		entityView.setController(this);
		addView(entityView);
	}

	public final ExecuteService<Event, T> getEntityServiceActions() {
		return getComponentModel().entityServiceActions;
	}

	public final void setEntityServiceActions(final ExecuteService<Event, T> entityServiceActions) {
		getOrCreateComponentModel().entityServiceActions = entityServiceActions;
	}

	@Override
	protected void checkConfig() {
		super.checkConfig();
		if (getEntityActionView() == null) {
			throw new IllegalStateException("An entity control view has not been set.");
		}
		if (getEntityView() == null) {
			throw new IllegalStateException("A entity view has not been set.");
		}
		if (getEntityServiceActions() == null) {
			throw new IllegalStateException("Entity service actions have not been set.");
		}
	}

	@Override
	public void configViews() {
		super.configViews();
		getEntityView().makeContentInvisible();
	}

	@Override
	public void configAjax(final WView view) {
		super.configAjax(view);
		view.addEventTarget(getViewMessages());
		view.addEventTarget(getEntityActionView());
		view.addEventTarget(getEntityView());
	}

	protected void handleEntityActionEvent(final Event event) {
		ActionEventType type = (ActionEventType) event.getEventType();
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
			case SAVE:
				handleSaveAction();
				break;
			case ADD:
				handleAddAction();
				break;
			case LOAD:
				handleLoadAction((T) event.getData());
				break;
		}
	}

	protected void handleBackAction() {
		reset();
	}

	protected void handleCancelAction() {
		EntityView<T> view = getEntityView();
		if (view.getEntityMode() == EntityMode.EDIT) {
			T bean = view.getViewBean();
			resetViews();
			handleLoadAction(bean);
			return;
		}
		resetViews();
	}

	protected void handleEditAction() {
		EntityView<T> view = getEntityView();
		if (view.isLoaded()) {
			changeViewMode(EntityMode.EDIT);
		}
	}

	protected void handleDeleteAction() {
		EntityView<T> view = getEntityView();
		if (!view.isLoaded()) {
			return;
		}
		changeViewMode(EntityMode.VIEW);
		T bean = view.getViewBean();
		try {
			doServiceAction(new Event(ActionEventType.DELETE, bean));
			dispatchCtrlEvent(ActionEventType.DELETE_OK, bean);
			getViewMessages().success("Delete OK.");
			resetViews();
		} catch (Exception e) {
			getViewMessages().error("Delete failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.DELETE_ERROR, bean, e);
		}
	}

	protected void handleRefreshAction() {
		EntityView<T> view = getEntityView();
		if (!view.isLoaded()) {
			return;
		}
		T bean = view.getViewBean();
		resetViews();
		try {
			bean = doServiceAction(new Event(ActionEventType.REFRESH, bean));
			handleLoadAction(bean);
			getViewMessages().success("Refreshed OK.");
			dispatchCtrlEvent(ActionEventType.REFRESH_OK, bean);
		} catch (Exception e) {
			getViewMessages().error("Refresh failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.REFRESH_ERROR, bean, e);
		}
	}

	protected void handleSaveAction() {
		EntityView<T> view = getEntityView();
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
			bean = doServiceAction(new Event(ActionEventType.SAVE, bean));
			dispatchCtrlEvent(ActionEventType.SAVE_OK, bean);
			getViewMessages().success("Saved OK.");
			handleLoadAction(bean);
		} catch (Exception e) {
			getViewMessages().error("Save failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.SAVE_ERROR, e);
		}
	}

	protected void handleAddAction() {
		resetViews();
		try {
			T bean = doServiceAction(new Event(ActionEventType.ADD));
			handleLoadAction(bean);
			changeViewMode(EntityMode.ADD);
		} catch (Exception e) {
			getViewMessages().error("Refresh failed. " + e.getMessage());
			dispatchCtrlEvent(ActionEventType.LOAD_ERROR, e);
		}
	}

	protected void handleLoadAction(final T entity) {
		resetViews();
		getEntityView().setViewBean(entity);
		getEntityView().makeContentVisible();
		changeViewMode(EntityMode.VIEW);
		dispatchCtrlEvent(ActionEventType.LOAD_OK, entity);
	}

	protected void changeViewMode(final EntityMode mode) {
		EntityView entityView = getEntityView();
		entityView.setEntityMode(mode);
		// Keep Action View in SYNC
		EntityActionView actionView = getEntityActionView();
		actionView.setEntityMode(entityView.getEntityMode());
		actionView.setEntityLoaded(entityView.isLoaded());
	}

	protected T doServiceAction(final Event event) {
		return getEntityServiceActions().executeService(event);
	}

	@Override
	protected EntityCtrlModel<T> newComponentModel() {
		return new EntityCtrlModel();
	}

	@Override
	protected EntityCtrlModel<T> getComponentModel() {
		return (EntityCtrlModel<T>) super.getComponentModel();
	}

	@Override
	protected EntityCtrlModel<T> getOrCreateComponentModel() {
		return (EntityCtrlModel<T>) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class EntityCtrlModel<T> extends CtrlModel {

		private EntityActionView entityActionView;

		private EntityView<T> entityView;

		private ExecuteService<Event, T> entityServiceActions;

	}

}
