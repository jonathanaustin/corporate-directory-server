package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.ViewCombo;
import com.github.bordertech.wcomponents.lib.flux.wc.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.wc.WViewContent;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.RequiresActionModel;

/**
 *
 * @author jonathan
 */
public class EntityWithToolbarView<T> extends DefaultView implements MessageContainer, ViewCombo, EntityView<T>, RequiresActionModel<T> {

	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	private final ToolbarView toolbarView;

	private final EntityWithToolbarCtrl<T> ctrl;

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier) {
		this(dispatcher, qualifier, new DefaultEntityView<T>(dispatcher, qualifier));
	}

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView) {
		this(dispatcher, qualifier, entityView, new DefaultToolbarView(dispatcher, qualifier));
	}

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView, final ToolbarView actionView) {
		super(dispatcher, qualifier);

		this.entityView = entityView;
		this.toolbarView = actionView;
		this.ctrl = new EntityWithToolbarCtrl(dispatcher, qualifier);

		ctrl.setToolbarView(actionView);
		ctrl.setEntityView(entityView);

		WViewContent holder = getContent();
		holder.add(messages);
		holder.add(ctrl);
		holder.add(actionView);
		holder.add(entityView);
	}

	@Override
	public void makeContentVisible() {
		super.makeContentVisible();
		if (entityView.isLoaded()) {
			entityView.makeContentVisible();
		} else {
			entityView.makeContentInvisible();
		}
	}

	public final EntityView<T> getEntityView() {
		return entityView;
	}

	public final ToolbarView getToolbarView() {
		return toolbarView;
	}

	@Override
	public EntityMode getEntityMode() {
		return entityView.getEntityMode();
	}

	@Override
	public void setEntityMode(final EntityMode mode) {
		entityView.setEntityMode(mode);
	}

	@Override
	public boolean isFormReadOnly() {
		return entityView.isFormReadOnly();
	}

	@Override
	public boolean isLoaded() {
		return entityView.isLoaded();
	}

	@Override
	public void loadEntity(final T entity) {
		entityView.loadEntity(entity);
	}

	@Override
	public T getViewBean() {
		return entityView.getViewBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		entityView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		entityView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return entityView.validateView();
	}

	@Override
	public ActionModel<T> getActionModel() {
		return ctrl.getActionModel();
	}

	@Override
	public void setActionModel(final ActionModel<T> actionModel) {
		ctrl.setActionModel(actionModel);
	}

	@Override
	public void configViews() {
		ctrl.configViews();
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
