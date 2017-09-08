package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Matcher;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Holds the extrinsic state information of the edit view.
 */
public class DefaultDispatcherModel implements DispatcherModel {

	// Listeners that have EventType and Qualifier
	private Map<Matcher, List<ListenerWrapper>> listenersByMatcher;
	// Listeners that only have a match to EventType
	private Map<EventType, List<ListenerWrapper>> listenersByType;
	// Listeners that only match to the qualifier
	private Map<String, List<ListenerWrapper>> listenersByQualifiers;
	// Listeners by ID
	private Map<String, ListenerWrapper> listenersById;
	private Queue<Event> queuedEvents;
	private boolean dispatching;

	@Override
	public Map<Matcher, List<ListenerWrapper>> getListenersByMatcher() {
		if (listenersByMatcher == null) {
			listenersByMatcher = new HashMap<>();
		}
		return listenersByMatcher;
	}

	@Override
	public Map<EventType, List<ListenerWrapper>> getListenersByType() {
		if (listenersByType == null) {
			listenersByType = new HashMap<>();
		}
		return listenersByType;
	}

	@Override
	public Map<String, List<ListenerWrapper>> getListenersByQualifiers() {
		if (listenersByQualifiers == null) {
			listenersByQualifiers = new HashMap<>();
		}
		return listenersByQualifiers;
	}

	@Override
	public Map<String, ListenerWrapper> getListenersById() {
		if (listenersById == null) {
			listenersById = new HashMap<>();
		}
		return listenersById;
	}

	@Override
	public Queue<Event> getQueuedEvents() {
		if (queuedEvents == null) {
			queuedEvents = new ArrayDeque<>();
		}
		return queuedEvents;
	}

	@Override
	public boolean isDispatching() {
		return dispatching;
	}

	@Override
	public void setDispatching(boolean disaptching) {
		this.dispatching = disaptching;
	}

	@Override
	public void clearListenersByMatcher() {
		listenersByMatcher = null;
	}

	@Override
	public void clearListenersByType() {
		listenersByType = null;
	}

	@Override
	public void clearListenersByQualifiers() {
		listenersByQualifiers = null;
	}

	@Override
	public void clearListenersById() {
		listenersById = null;
	}

	@Override
	public void clearQueuedEvents() {
		queuedEvents = null;
	}

	@Override
	public boolean hasListenersByMatcher() {
		return listenersByMatcher != null && !listenersByMatcher.isEmpty();
	}

	@Override
	public boolean hasListenersByType() {
		return listenersByType != null && !listenersByType.isEmpty();
	}

	@Override
	public boolean hasListenersByQualifiers() {
		return listenersByQualifiers != null && !listenersByQualifiers.isEmpty();
	}

	@Override
	public boolean hasListenersById() {
		return listenersById != null && !listenersById.isEmpty();
	}

	@Override
	public boolean hasQueuedEvents() {
		return queuedEvents != null && !queuedEvents.isEmpty();
	}

}
