package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.app.action.ModifyActionType;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.CreatorKey;

/**
 * Modify action creator used by views.
 *
 * @author jonathan
 */
public class AbstractModifyCreator implements ModifyCreator {

	private final CreatorKey key;

	public AbstractModifyCreator(final CreatorKey key) {
		this.key = key;
	}

	@Override
	public CreatorKey getKey() {
		return key;
	}

	public String getQualifier() {
		return getKey().getQualifier();
	}

	protected void dispatchModifyAction(final ModifyActionType actionType, final Object entity) {
		DefaultAction action = new DefaultAction(new ActionKey(actionType, getQualifier()), entity);
		getDispatcher().dispatch(action);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

}
