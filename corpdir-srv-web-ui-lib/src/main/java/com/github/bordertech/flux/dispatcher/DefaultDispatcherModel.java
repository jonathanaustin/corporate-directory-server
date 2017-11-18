package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;
import com.github.bordertech.flux.key.StoreKey;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Holds the dispatcher state.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDispatcherModel implements DispatcherModel {

	// Listeners that have ActionType and Qualifier
	private final Map<ActionKey, List<ListenerWrapper>> listenersByKey = new HashMap<>();
	// Listeners that only have a match to ActionType
	private final Map<ActionType, List<ListenerWrapper>> listenersByType = new HashMap<>();
	// Listeners that only match to the qualifier
	private final Map<String, List<ListenerWrapper>> listenersByQualifiers = new HashMap<>();
	// Listeners by ID
	private final Map<String, ListenerWrapper> listenersById = new HashMap<>();

	// Stores that have StoreType and Qualifier
	private final Map<StoreKey, Store> storesByKey = new HashMap<>();

	private final Queue<Action> queuedActions = new ArrayDeque<>();
	private boolean dispatching;

	@Override
	public Map<ActionKey, List<ListenerWrapper>> getListenersByKey() {
		return listenersByKey;
	}

	@Override
	public Map<ActionType, List<ListenerWrapper>> getListenersByType() {
		return listenersByType;
	}

	@Override
	public Map<String, List<ListenerWrapper>> getListenersByQualifiers() {
		return listenersByQualifiers;
	}

	@Override
	public Map<String, ListenerWrapper> getListenersById() {
		return listenersById;
	}

	@Override
	public Queue<Action> getQueuedActions() {
		return queuedActions;
	}

	@Override
	public boolean isDispatching() {
		return dispatching;
	}

	@Override
	public void setDispatching(final boolean dispatching) {
		this.dispatching = dispatching;
	}

	public boolean hasQueuedActions() {
		return !queuedActions.isEmpty();
	}

	@Override
	public Map<StoreKey, Store> getStoresByKey() {
		return storesByKey;
	}

}
