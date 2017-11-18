package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.base.StateBaseActionType;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;
import com.github.bordertech.flux.key.StoreKey;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultStore implements Store {

	private final StoreKey storeKey;
	private final Set<String> registeredIds = new HashSet<>();

	public DefaultStore(final StoreKey storeKey) {
		this.storeKey = storeKey;
	}

	@Override
	public StoreKey getKey() {
		return storeKey;
	}

	@Override
	public void registerListeners() {
		// Do nothing
	}

	@Override
	public void unregisterListeners() {
		Dispatcher dispatcher = getDispatcher();
		for (String id : registeredIds) {
			dispatcher.unregisterListener(id);
		}
	}

	@Override
	public void dispatchChangeAction(final ActionType actionType) {
		DefaultAction action = new DefaultAction(new ActionKey(StateBaseActionType.STORE_CHANGED, storeKey.getQualifier()), actionType);
		getDispatcher().dispatch(action);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	/**
	 * A helper method to register a listener with an Action Type and the Controller qualifier automatically added.
	 *
	 * @param listener
	 * @param actionType
	 */
	protected void registerListener(final ActionType actionType, final Listener listener) {
		String id = getDispatcher().registerListener(new ActionKey(actionType, storeKey.getQualifier()), listener);
		registeredIds.add(id);
	}

}