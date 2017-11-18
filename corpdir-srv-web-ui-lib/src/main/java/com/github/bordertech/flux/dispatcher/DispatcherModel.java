package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;
import com.github.bordertech.flux.key.StoreKey;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DispatcherModel {

	// Listeners that have ActionType and Qualifier
	Map<ActionKey, List<ListenerWrapper>> getListenersByKey();

	// Listeners that only have a match to ActionType
	Map<ActionType, List<ListenerWrapper>> getListenersByType();

	// Listeners that only match to the qualifier
	Map<String, List<ListenerWrapper>> getListenersByQualifiers();

	// Listeners by ID
	Map<String, ListenerWrapper> getListenersById();

	Queue<Action> getQueuedActions();

	boolean isDispatching();

	void setDispatching(final boolean disaptching);

	Map<StoreKey, Store> getStoresByKey();

}
