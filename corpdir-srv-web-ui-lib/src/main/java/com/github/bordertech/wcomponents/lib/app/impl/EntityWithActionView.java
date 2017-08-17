package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithActionCtrl;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.impl.ExecuteService;

/**
 *
 * @author jonathan
 */
public abstract class EntityWithActionView<T> extends DefaultView<T> implements MessageContainer, EntityView<T> {
	
	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	private final EntityActionView actionView;

	public EntityWithActionView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public EntityWithActionView(final Dispatcher dispatcher, final String qualifier) {
		this(dispatcher, qualifier, new EntityBaseView<T>(dispatcher, qualifier), new EntityActionMenuView(dispatcher, qualifier));
	}

	public EntityWithActionView(final Dispatcher dispatcher, final String qualifier, final EntityView<T> entityView, final EntityActionView actionView) {
		super(dispatcher, qualifier);
		this.entityView = entityView;
		this.actionView = actionView;

		EntityWithActionCtrl ctrl = new EntityWithActionCtrl(dispatcher, qualifier);
		ctrl.setEntityActionView(actionView);
		ctrl.setEntityView(entityView);

		ExecuteService<Event, T> service = new ExecuteService<Event, T>() {
			@Override
			public T executeService(final Event criteria) {
				return doService((ActionEventType) criteria.getEventType(), (T) criteria.getData());
			}
		};
		ctrl.setEntityServiceActions(service);

		ctrl.configViews();

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

	public abstract T doService(final ActionEventType type, final T bean);

}
