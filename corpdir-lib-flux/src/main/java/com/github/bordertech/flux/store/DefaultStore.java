package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.base.StateBaseActionType;
import com.github.bordertech.flux.factory.FluxFactory;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;
import java.util.Set;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultStore implements Store {

	private final String key;

	public DefaultStore(final String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		// Do nothing
	}

	@Override
	public void dispatchChangeAction(final ActionType actionType) {
		DefaultAction action = new DefaultAction(new ActionKey(StateBaseActionType.STORE_CHANGED, getKey()), actionType);
		getDispatcher().dispatch(action);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return FluxFactory.getDispatcher();
	}

	/**
	 * A helper method to register a listener with an Action Type and the Controller qualifier automatically added.
	 *
	 * @param listener the listener to register
	 * @param actionType the action type
	 * @return the listener id
	 */
	protected String registerListener(final ActionType actionType, final Listener listener) {
		return getDispatcher().registerListener(new ActionKey(actionType, getKey()), listener);
	}

}
