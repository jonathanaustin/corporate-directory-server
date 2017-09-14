package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.Matcher;
import com.github.bordertech.wcomponents.lib.flux.Qualifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class DispatcherUtil {

	private DispatcherUtil() {
	}

	public static void registerDispatcherListener(final Listener listener, final DispatcherEventType eventType, final DispatcherModel model) {
		ListenerWrapper wrapper = new ListenerWrapper(new EventMatcher(eventType), listener);
		handleRegisterListener(wrapper, model);
	}

	public static ListenerWrapper getListener(final String registerId, final DispatcherModel model) {
		if (model.hasListenersById()) {
			return model.getListenersById().get(registerId);
		}
		return null;
	}

	public static void handleRegisterListener(final ListenerWrapper wrapper, final DispatcherModel model) {
		// Register via the wrapper ID
		model.getListenersById().put(wrapper.getRegisterId(), wrapper);
		Qualifier matcher = wrapper.getMatcher().getMatchQualifier();
		// Register by the matcher details
		if (matcher.getEventType() != null && matcher.getQualifier() != null) {
			registerListenerInMap(model.getListenersByMatcher(), wrapper.getMatcher(), wrapper);
		} else if (matcher.getEventType() != null) {
			registerListenerInMap(model.getListenersByType(), matcher.getEventType(), wrapper);
		} else {
			registerListenerInMap(model.getListenersByQualifiers(), matcher.getQualifier(), wrapper);
		}
	}

	public static void handleUnregisterListener(final String registerId, final DispatcherModel model) {
		// Check it exists
		ListenerWrapper wrapper = getListener(registerId, model);
		if (wrapper == null) {
			return;
		}
		model.getListenersById().remove(registerId);
		if (model.getListenersById().isEmpty()) {
			model.clearListenersById();
		}
		// Unregister by the matcher details
		Qualifier matcher = wrapper.getMatcher().getMatchQualifier();
		if (matcher.getEventType() != null && matcher.getQualifier() != null) {
			if (unregisterListenerFromMap(model.getListenersByMatcher(), wrapper.getMatcher(), wrapper)) {
				model.clearListenersByMatcher();
			}
		} else if (matcher.getEventType() != null) {
			if (unregisterListenerFromMap(model.getListenersByType(), matcher.getEventType(), wrapper)) {
				model.clearListenersByType();
			}
		} else {
			if (unregisterListenerFromMap(model.getListenersByQualifiers(), matcher.getQualifier(), wrapper)) {
				model.clearListenersByQualifiers();
			}
		}
	}

	public static void dispatch(final Event event, final DispatcherModel model) {
		model.getQueuedEvents().add(event);
		if (!model.isDispatching()) {
			processQueue(model);
		}
	}

	private static void processQueue(final DispatcherModel model) {
		model.setDispatching(true);
		try {
			Event next = model.getQueuedEvents().poll();
			while (next != null) {
				notifyListeners(next, model);
				next = model.getQueuedEvents().poll();
			}
			model.clearQueuedEvents();
		} finally {
			model.setDispatching(false);
		}
	}

	private static void notifyListeners(final Event event, final DispatcherModel model) {
		List<ListenerWrapper> matches = getListenersForEvent(event.getQualifier(), model);
		for (ListenerWrapper wrapper : matches) {
			wrapper.getListener().handleEvent(event);
		}
	}

	private static List<ListenerWrapper> getListenersForEvent(final Qualifier qualifier, final DispatcherModel model) {
		List<ListenerWrapper> matches = new ArrayList<>();
		// Check listeners only matching on Type
		if (model.hasListenersByType()) {
			matches.addAll(getListenersFromMap(model.getListenersByType(), qualifier.getEventType()));
		}
		if (qualifier.getQualifier() != null) {
			// Check listeners with Type and Qualifier
			if (model.hasListenersByMatcher()) {
				Matcher matcher = new EventMatcher(qualifier.getEventType(), qualifier.getQualifier());
				matches.addAll(getListenersFromMap(model.getListenersByMatcher(), matcher));
			}
			// Check listeners only matching on Qualifier
			if (model.hasListenersByQualifiers()) {
				matches.addAll(getListenersFromMap(model.getListenersByQualifiers(), qualifier.getQualifier()));
			}
		}
		return Collections.unmodifiableList(matches);
	}

	private static <T> List<ListenerWrapper> getListenersFromMap(final Map<T, List<ListenerWrapper>> map, final T key) {
		if (map == null || key == null) {
			return Collections.EMPTY_LIST;
		}
		List<ListenerWrapper> listeners = map.get(key);
		return listeners == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(listeners);
	}

	private static <T> void registerListenerInMap(final Map<T, List<ListenerWrapper>> orig, final T key, final ListenerWrapper listener) {
		if (key == null) {
			throw new IllegalArgumentException("A Key value must be provided.");
		}
		if (listener == null) {
			throw new IllegalArgumentException("A Listener must be provided.");
		}
		Map<T, List<ListenerWrapper>> map = orig == null ? new HashMap<T, List<ListenerWrapper>>() : orig;
		List<ListenerWrapper> listeners = map.get(key);
		if (listeners == null) {
			listeners = new ArrayList<>();
			map.put(key, listeners);
		}
		listeners.add(listener);
	}

	private static <T> boolean unregisterListenerFromMap(final Map<T, List<ListenerWrapper>> map, final T key, final ListenerWrapper listener) {
		if (map != null && key != null) {
			List<ListenerWrapper> listeners = map.get(key);
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty()) {
					map.remove(key);
				}
			}
			return map.isEmpty();
		}
		return true;
	}

}
