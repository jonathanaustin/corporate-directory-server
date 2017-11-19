package com.github.bordertech.flux.app.actioncreator.impl;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.app.action.ModifyActionType;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.app.actioncreator.ModifyActionCreator;

/**
 * Modify action creator used by views.
 *
 * @author jonathan
 */
public abstract class AbstractModifyActionCreator implements ModifyActionCreator {

	private final String key;

	public AbstractModifyActionCreator(final String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

	protected void dispatchModifyAction(final ModifyActionType actionType, final Object entity) {
		DefaultAction action = new DefaultAction(new ActionKey(actionType, getKey()), entity);
		getDispatcher().dispatch(action);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

}
