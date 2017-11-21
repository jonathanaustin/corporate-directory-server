package com.github.bordertech.flux.crud.actioncreator.impl;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.crud.action.ModifyActionType;
import com.github.bordertech.flux.crud.actioncreator.ModifyActionCreator;
import com.github.bordertech.flux.factory.DispatcherFactory;
import com.github.bordertech.flux.key.ActionKey;

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
