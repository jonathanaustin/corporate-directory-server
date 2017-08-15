package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.lib.app.type.EntityActionStatus;
import com.github.bordertech.wcomponents.lib.app.type.EntityActionType;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.view.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.View;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.flux.impl.ExecuteService;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for an Entity View and Entity Action view.
 *
 * @param <T> the entity type
 * @author jonathan
 */
public class EntityActionCtrl<T> extends DefaultController {

	public EntityActionCtrl(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public EntityActionCtrl(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

		// Entity Action Event Listeners
		for (EntityActionType event : EntityActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleEntityActionEvent(event);
				}
			};
			registerCtrlListener(listener, event);
		}
	}

	@Override
	public List<AjaxTarget> getEventTargets(final View view, final EventType eventType) {
		List<AjaxTarget> targets = new ArrayList<>();
		targets.add(getEntityActionView());
		targets.add(getEntityView());
		targets.add(getViewMessages());
		return targets;
	}

	public final EntityActionView getEntityActionView() {
		return getComponentModel().entityActionView;
	}

	public final void setEntityActionView(final EntityActionView actionView) {
		getOrCreateComponentModel().entityActionView = actionView;
	}

	public final EntityView<T> getEntityView() {
		return getComponentModel().entityView;
	}

	public final void setEntityView(final EntityView<T> entityView) {
		getOrCreateComponentModel().entityView = entityView;
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

	protected void handleEntityActionEvent(final Event event) {
		EntityActionType type = (EntityActionType) event.getEventType();
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
				handleNewAction();
				break;
		}
	}

	protected void handleBackAction() {
		resetViews();
	}

	protected void handleCancelAction() {
		EntityView<T> view = getEntityView();
		// TODO might need to reload BEAN
		T bean = view.getViewBean();
		resetViews();
		view.setViewBean(bean);
	}

	protected void handleEditAction() {
		changeViewMode(EntityMode.EDIT);
	}

	protected void handleDeleteAction() {
		changeViewMode(EntityMode.DELETE);
		T bean = getEntityView().getViewBean();
		try {
			doServiceAction(new Event(EntityActionType.DELETE, bean));
			dispatchCtrlEvent(EntityActionStatus.DELETE_OK, bean);
		} catch (Exception e) {
			getViewMessages().error("Delete failed. " + e.getMessage());
			dispatchCtrlEvent(EntityActionStatus.DELETE_ERROR, e);
		}
	}

	protected void handleRefreshAction() {
		T bean = getEntityView().getViewBean();
		resetViews();
		changeViewMode(EntityMode.VIEW);
		try {
			bean = doServiceAction(new Event(EntityActionType.REFRESH, bean));
			dispatchCtrlEvent(EntityActionStatus.REFRESH_OK, bean);
		} catch (Exception e) {
			getViewMessages().error("Refresh failed. " + e.getMessage());
			getEntityView().setViewBean(bean);
			dispatchCtrlEvent(EntityActionStatus.REFRESH_ERROR, e);
		}
	}

	protected void handleSaveAction() {
		// Do Validation
		T bean = getEntityView().getViewBean();
		try {
			bean = doServiceAction(new Event(EntityActionType.SAVE, bean));
			dispatchCtrlEvent(EntityActionStatus.SAVE_OK, bean);
		} catch (Exception e) {
			getViewMessages().error("Save failed. " + e.getMessage());
			dispatchCtrlEvent(EntityActionStatus.SAVE_ERROR, e);
		}
	}

	protected void handleNewAction() {
		resetViews();
		try {
			T bean = doServiceAction(new Event(EntityActionType.ADD));
			changeViewMode(EntityMode.ADD);
			getEntityView().setViewBean(bean);
			dispatchCtrlEvent(EntityActionStatus.ADD_OK, bean);
		} catch (Exception e) {
			getViewMessages().error("Refresh failed. " + e.getMessage());
			dispatchCtrlEvent(EntityActionStatus.ADD_ERROR, e);
		}

	}

	protected void changeViewMode(final EntityMode mode) {
		EntityView entityView = getEntityView();
		entityView.setEntityMode(mode);
		entityView.doRefreshViewState();

		EntityActionView actionView = getEntityActionView();
		actionView.setEntityMode(mode);
	}

	protected void resetViews() {
		getEntityActionView().reset();
		getEntityView().reset();
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
