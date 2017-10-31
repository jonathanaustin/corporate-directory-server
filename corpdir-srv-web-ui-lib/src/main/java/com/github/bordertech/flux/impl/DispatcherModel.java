package com.github.bordertech.flux.impl;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.StoreKey;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DispatcherModel {

	// Listeners that have EventType and Qualifier
	Map<EventKey, List<ListenerWrapper>> getListenersByKey();

	// Listeners that only have a match to EventType
	Map<EventType, List<ListenerWrapper>> getListenersByType();

	// Listeners that only match to the qualifier
	Map<String, List<ListenerWrapper>> getListenersByQualifiers();

	// Listeners by ID
	Map<String, ListenerWrapper> getListenersById();

	Queue<Event> getQueuedEvents();

	boolean isDispatching();

	void setDispatching(final boolean disaptching);

	Map<StoreKey, Store> getStoresByKey();

}
