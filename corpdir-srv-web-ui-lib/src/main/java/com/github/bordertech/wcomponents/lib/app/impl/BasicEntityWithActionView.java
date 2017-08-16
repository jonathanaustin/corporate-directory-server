package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithActionCtrl;
import com.github.bordertech.wcomponents.lib.app.type.ActionEventType;
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
public abstract class BasicEntityWithActionView<T> extends DefaultView<T> implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	private final EntityActionView actionView;

	public BasicEntityWithActionView(final Dispatcher dispatcher) {
		this(dispatcher, new DefaultEntityView<T>(dispatcher), new DefaultEntityActionView(dispatcher));
	}

	public BasicEntityWithActionView(final Dispatcher dispatcher, final EntityView<T> entityView, final EntityActionView actionView) {
		super(dispatcher);
		this.entityView = entityView;
		this.actionView = actionView;

		EntityWithActionCtrl ctrl = new EntityWithActionCtrl(dispatcher);
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
