package com.github.bordertech.flux.actioncreator.impl;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.action.ActionKey;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.type.ModifyStoreActionType;

/**
 * Modify action creator used by views.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultActionCreator implements ActionCreator {

	private static final Dispatcher DISPATCHER = Didums.getService(Dispatcher.class);

	private final String key;

	/**
	 * @param key the action creator key
	 */
	public DefaultActionCreator(final String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DISPATCHER;
	}

	/**
	 * Helper method to dispatch store actions.
	 *
	 * @param actionType the action type
	 * @param data the action data
	 */
	protected void dispatchModifyAction(final ModifyStoreActionType actionType, final Object data) {
		DefaultAction action = new DefaultAction(new ActionKey(actionType, getKey()), data);
		getDispatcher().dispatch(action);
	}

}
