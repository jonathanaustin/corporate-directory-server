package com.github.bordertech.flux.actioncreator;

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

	public DefaultActionCreator(final String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

	protected void dispatchModifyAction(final ModifyStoreActionType actionType, final Object entity) {
		DefaultAction action = new DefaultAction(new ActionKey(actionType, getKey()), entity);
		getDispatcher().dispatch(action);
	}

	protected final Dispatcher getDispatcher() {
		return DISPATCHER;
	}

}
