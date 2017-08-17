package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithActionCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.RequiresActionModel;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;

/**
 *
 * @author jonathan
 */
public class EntityWithActionView<T> extends DefaultView<T> implements MessageContainer, EntityView<T>, RequiresActionModel<T> {

	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	private final EntityActionView actionView;

	private final EntityWithActionCtrl<T> ctrl;

	public EntityWithActionView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView, final EntityActionView actionView) {
		super(dispatcher, qualifier);

		this.entityView = entityView;
		this.actionView = actionView;
		this.ctrl = new EntityWithActionCtrl(dispatcher, qualifier);

		ctrl.setEntityActionView(actionView);
		ctrl.setEntityView(entityView);

		WDiv holder = getContent();
		holder.add(messages);
		holder.add(ctrl);
		holder.add(actionView);
		holder.add(entityView);
	}

	public final EntityView<T> getEntityView() {
		return entityView;
	}

	public final EntityActionView getActionView() {
		return actionView;
	}

	@Override
	public WMessages getMessages() {
		return messages;
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
	public ActionModel<T> getActionModel() {
		return ctrl.getActionModel();
	}

	@Override
	public void setActionModel(ActionModel<T> actionModel) {
		ctrl.setActionModel(actionModel);
	}

	public void configViews() {
		ctrl.configViews();
	}

}
