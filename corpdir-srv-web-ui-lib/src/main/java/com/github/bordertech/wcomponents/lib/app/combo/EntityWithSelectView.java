package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.ViewCombo;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.RequiresActionModel;

/**
 *
 * @author jonathan
 */
public class EntityWithSelectView<S, T> extends DefaultView implements MessageContainer, ViewCombo, EntityView<T>, RequiresActionModel<T> {

	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	private final SelectView<T> selectView;

	private final EntityWithSelectCtrl<S, T> ctrl;

	public EntityWithSelectView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView, final SelectView<T> selectView) {
		super(dispatcher, qualifier);

		this.entityView = entityView;
		this.selectView = selectView;
		this.ctrl = new EntityWithSelectCtrl<>(dispatcher, qualifier);

		ctrl.setEntityView(entityView);
		ctrl.setSelectView(selectView);

		WDiv content = getContent();
		content.add(messages);
		content.add(ctrl);
		content.add(selectView);
		content.add(entityView);
	}

	public final EntityView<T> getEntityView() {
		return entityView;
	}

	public final SelectView<T> getSelectView() {
		return selectView;
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
	public void configViewDefaults() {
		ctrl.configViews();
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
	public WMessages getMessages() {
		return messages;
	}

}
