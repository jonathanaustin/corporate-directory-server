package com.github.bordertech.flux.impl;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.Matcher;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author jonathan
 */
public interface DispatcherModel {

	// Listeners that have EventType and Qualifier
	Map<Matcher, List<ListenerWrapper>> getListenersByMatcher();

	// Listeners that only have a match to EventType
	Map<EventType, List<ListenerWrapper>> getListenersByType();

	// Listeners that only match to the qualifier
	Map<String, List<ListenerWrapper>> getListenersByQualifiers();

	// Listeners by ID
	Map<String, ListenerWrapper> getListenersById();

	Queue<Event> getQueuedEvents();

	boolean isDispatching();

	void setDispatching(final boolean disaptching);

	void clearListenersByMatcher();

	void clearListenersByType();

	void clearListenersByQualifiers();

	void clearListenersById();

	void clearQueuedEvents();

	boolean hasListenersByMatcher();

	boolean hasListenersByType();

	boolean hasListenersByQualifiers();

	boolean hasListenersById();

	boolean hasQueuedEvents();

}
