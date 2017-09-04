package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityToolbarView;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.mvc.impl.MessageCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;

/**
 *
 * @author jonathan
 */
public class EntityWithToolbarView<T> extends DefaultComboView implements EntityView<T> {

	private final MessageCtrl messageCtrl;

	private final EntityView<T> entityView;

	private final EntityToolbarView toolbarView;

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier) {
		this(dispatcher, qualifier, new DefaultEntityView<T>(dispatcher, qualifier));
	}

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView) {
		this(dispatcher, qualifier, entityView, new DefaultEntityToolbarView(dispatcher, qualifier));
	}

	public EntityWithToolbarView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView, final EntityToolbarView toolbarView) {
		super("wclib/hbs/layout/combo-ent-toolbar.hbs", dispatcher, qualifier);

		// Messages (default to show all)
		this.messageCtrl = new MessageCtrl(dispatcher, qualifier);
		messageCtrl.setMessageView(new DefaultMessageView(dispatcher, qualifier));

		// Views
		this.entityView = entityView;
		this.toolbarView = toolbarView;

		// Ctrl
		EntityWithToolbarCtrl<T> ctrl = new EntityWithToolbarCtrl(dispatcher, qualifier);
		ctrl.setToolbarView(toolbarView);
		ctrl.setEntityView(entityView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", messageCtrl.getMessageView());
		content.addTaggedComponent("vw-ctrl-msg", messageCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-entity", entityView);

		// Default visibility
		entityView.setContentVisible(false);
	}

	public final MessageCtrl getMessageCtrl() {
		return messageCtrl;
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

	public final EntityToolbarView getToolbarView() {
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

}
