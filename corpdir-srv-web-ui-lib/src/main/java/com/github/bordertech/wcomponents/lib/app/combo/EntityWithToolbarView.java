package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.model.Model;
import com.github.bordertech.wcomponents.lib.mvc.ViewCombo;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 *
 * @author jonathan
 */
public class EntityWithToolbarView<T> extends DefaultView implements MessageContainer, ViewCombo, EntityView<T> {

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

		WContainer holder = getContent();
		holder.add(messages);
		holder.add(ctrl);
		holder.add(actionView);
		holder.add(entityView);
	}

	@Override
	public void setContentVisible(final boolean visible) {
		super.setContentVisible(true);
		// Keep toolbar visible for the "ADD" button
		toolbarView.setContentVisible(true);
		entityView.setContentVisible(visible && entityView.isLoaded());
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
	public void loadEntity(final T entity, final EntityMode mode) {
		entityView.loadEntity(entity, mode);
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
	public WContainer getEntityDetailsHolder() {
		return entityView.getEntityDetailsHolder();
	}

	@Override
	public void configComboViews() {
		ctrl.configViews();
	}

	@Override
	public void addModel(final Model model) {
		ctrl.addModel(model);
	}

	@Override
	public void makeBlocking() {
		ctrl.setBlocking(true);
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
