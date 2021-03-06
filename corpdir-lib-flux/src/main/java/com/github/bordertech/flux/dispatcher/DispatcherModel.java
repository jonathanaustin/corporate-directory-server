package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.ActionKey;
import com.github.bordertech.flux.action.ActionType;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Holds the dispatcher state.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DispatcherModel implements Serializable {

	// Listeners that have ActionType and Qualifier
	private final Map<ActionKey, List<ListenerWrapper>> listenersByKey = new HashMap<>();
	// Listeners that only have a match to ActionType
	private final Map<ActionType, List<ListenerWrapper>> listenersByType = new HashMap<>();
	// Listeners that only match to the qualifier
	private final Map<String, List<ListenerWrapper>> listenersByQualifiers = new HashMap<>();
	// Listeners by ID
	private final Map<String, ListenerWrapper> listenersById = new HashMap<>();

	private final Map<String, Store> storesByKey = new HashMap<>();
	private final Map<String, Set<String>> storeRegisteredIds = new HashMap<>();
	private final Map<String, ActionCreator> creatorsByKey = new HashMap<>();

	private final Queue<Action> queuedActions = new ArrayDeque<>();
	private boolean dispatching;

	public Map<ActionKey, List<ListenerWrapper>> getListenersByKey() {
		return listenersByKey;
	}

	public Map<ActionType, List<ListenerWrapper>> getListenersByType() {
		return listenersByType;
	}

	public Map<String, List<ListenerWrapper>> getListenersByQualifiers() {
		return listenersByQualifiers;
	}

	public Map<String, ListenerWrapper> getListenersById() {
		return listenersById;
	}

	public Queue<Action> getQueuedActions() {
		return queuedActions;
	}

	public boolean isDispatching() {
		return dispatching;
	}

	public void setDispatching(final boolean dispatching) {
		this.dispatching = dispatching;
	}

	public boolean hasQueuedActions() {
		return !queuedActions.isEmpty();
	}

	public Map<String, Store> getStoresByKey() {
		return storesByKey;
	}

	public Map<String, Set<String>> getStoreRegisteredIds() {
		return storeRegisteredIds;
	}

	public Map<String, ActionCreator> getActionCreatorsByKey() {
		return creatorsByKey;
	}

}
