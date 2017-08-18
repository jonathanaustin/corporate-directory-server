package com.github.bordertech.wcomponents.lib.app.comp;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.RequiresActionModel;

/**
 *
 * @author jonathan
 */
public class EntityWithToolbarView<T> extends DefaultView<T> implements MessageContainer, EntityView<T>, RequiresActionModel<T> {

	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	private final ToolbarView actionView;

	private final EntityWithToolbarCtrl<T> ctrl;

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView) {
		this(dispatcher, qualifier, entityView, new DefaultToolbarView(dispatcher, qualifier));
	}

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView, final ToolbarView actionView) {
		super(dispatcher, qualifier);

		this.entityView = entityView;
		this.actionView = actionView;
		this.ctrl = new EntityWithToolbarCtrl(dispatcher, qualifier);

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

	public final ToolbarView getActionView() {
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
