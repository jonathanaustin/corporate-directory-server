package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.DispatcherEvent;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDispatcher extends AbstractWComponent implements Dispatcher {

	public DefaultDispatcher() {

		// Listen for Register Event
		Listener listener = new Listener<RegisterEvent>() {
			@Override
			public void handleEvent(final RegisterEvent event) {
				handleRegister(event.getData());
			}
		};
		handleRegister(new ListenerWrapper(new EventMatcher(DispatcherEvent.REGISTER), listener));

		// Listen for Unregister Event
		listener = new Listener<UnRegisterEvent>() {
			@Override
			public void handleEvent(final UnRegisterEvent event) {
				handleUnregister(event.getData());
			}
		};
		handleRegister(new ListenerWrapper(new EventMatcher(DispatcherEvent.UNREGISTER), listener));

	}

	@Override
	public final void dispatch(final Event event) {
		queueEvent(event);
		if (!isDispatching()) {
			processQueue();
		}
	}

	@Override
	public final boolean isDispatching() {
		return getComponentModel().dispatching;
	}

	@Override
	public final String register(final Listener listener, final EventType eventType) {
		return register(listener, new EventMatcher(eventType));
	}

	@Override
	public final String register(final Listener listener, final String qualifier) {
		return register(listener, new EventMatcher(qualifier));
	}

	@Override
	public final String register(final Listener listener, final EventMatcher matcher) {
		ListenerWrapper wrapper = new ListenerWrapper(matcher, listener);
		dispatch(new RegisterEvent(wrapper));
		return wrapper.getRegisterId();
	}

	@Override
	public final void unregister(final String registerId) {
		dispatch(new UnRegisterEvent(registerId));
	}

	protected void processQueue() {
		startDispatching();
		try {
			Event next = queueNext();
			while (next != null) {
				notifyListeners(next);
				next = queueNext();
			}
		} finally {
			stopDispatching();
		}
	}

	protected void startDispatching() {
		getOrCreateComponentModel().dispatching = true;
	}

	protected void stopDispatching() {
		getOrCreateComponentModel().dispatching = false;
	}

	protected void notifyListeners(final Event event) {
		for (ListenerWrapper wrapper : getListenersForEvent(event.getEventQualifier())) {
			wrapper.getListener().handleEvent(event);
		}
	}

	protected String handleRegister(final ListenerWrapper wrapper) {
		DispatcherModel model = getOrCreateComponentModel();
		// Register by ID
		if (model.listenersById == null) {
			model.listenersById = new HashMap<>();
		}
		model.listenersById.put(wrapper.getRegisterId(), wrapper);
		EventMatcher matcher = wrapper.getMatcher();
		// Register by which details it has
		if (matcher.getEventType() != null && matcher.getQualifier() != null) {
			model.listenersByMatcher = registerListenerInMap(model.listenersByMatcher, matcher, wrapper);
		} else if (matcher.getEventType() != null) {
			model.listenersByType = registerListenerInMap(model.listenersByType, matcher.getEventType(), wrapper);
		} else {
			model.listenersByQualifiers = registerListenerInMap(model.listenersByQualifiers, matcher.getQualifier(), wrapper);
		}
		return wrapper.getRegisterId();
	}

	protected void handleUnregister(final String registerId) {
		DispatcherModel model = getOrCreateComponentModel();
		// Unregister by ID
		ListenerWrapper wrapper = model.listenersById.remove(registerId);
		if (model.listenersById.isEmpty()) {
			model.listenersById = null;
		}
		if (wrapper != null) {
			// Unregister by what details it has
			EventMatcher matcher = wrapper.getMatcher();
			if (matcher.getEventType() != null && matcher.getQualifier() != null) {
				model.listenersByMatcher = unregisterListenerFromMap(model.listenersByMatcher, matcher, wrapper);
			} else if (matcher.getEventType() != null) {
				model.listenersByType = unregisterListenerFromMap(model.listenersByType, matcher.getEventType(), wrapper);
			} else {
				model.listenersByQualifiers = unregisterListenerFromMap(model.listenersByQualifiers, matcher.getQualifier(), wrapper);
			}
		}
	}

	protected List<ListenerWrapper> getListenersForEvent(final EventQualifier qualifier) {
		DispatcherModel model = getComponentModel();
		List<ListenerWrapper> matches = new ArrayList<>();
		if (qualifier.getQualifier() == null) {
			// Check listeners only matching on Type
			matches.addAll(getListenersFromMap(model.listenersByType, qualifier.getEventType()));
		} else {
			// Check listeners with Type and Qualifier
			EventMatcher matcher = new EventMatcher(qualifier.getEventType(), qualifier.getQualifier());
			matches.addAll(getListenersFromMap(model.listenersByMatcher, matcher));
			// Check listeners only matching on Qualifier
			matches.addAll(getListenersFromMap(model.listenersByQualifiers, qualifier.getQualifier()));
		}
		return Collections.unmodifiableList(matches);
	}

	protected static <T> List<ListenerWrapper> getListenersFromMap(final Map<T, List<ListenerWrapper>> map, final T key) {
		if (map == null || key == null) {
			return Collections.EMPTY_LIST;
		}
		List<ListenerWrapper> listeners = map.get(key);
		return listeners == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(listeners);
	}

	protected static <T> Map<T, List<ListenerWrapper>> registerListenerInMap(final Map<T, List<ListenerWrapper>> orig, final T key, final ListenerWrapper listener) {
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
		return map;
	}

	protected static <T> Map<T, List<ListenerWrapper>> unregisterListenerFromMap(final Map<T, List<ListenerWrapper>> map, final T key, final ListenerWrapper listener) {
		if (map != null && key != null) {
			List<ListenerWrapper> listeners = map.get(key);
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty()) {
					map.remove(key);
				}
			}
			return map.isEmpty() ? null : map;
		}
		return null;
	}

	protected void queueEvent(final Event event) {
		DispatcherModel model = getOrCreateComponentModel();
		if (model.queuedEvents == null) {
			model.queuedEvents = new ArrayDeque<>();
		}
		model.queuedEvents.add(event);
	}

	protected Event queueNext() {
		DispatcherModel model = getOrCreateComponentModel();
		if (model.queuedEvents != null) {
			Event event = model.queuedEvents.poll();
			if (model.queuedEvents.isEmpty()) {
				model.queuedEvents = null;
			}
			return event;
		}
		return null;
	}

	@Override
	protected DispatcherModel newComponentModel() {
		return new DispatcherModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DispatcherModel getComponentModel() {
		return (DispatcherModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DispatcherModel getOrCreateComponentModel() {
		return (DispatcherModel) super.getOrCreateComponentModel();

	}

	public static class ListenerWrapper implements Serializable {

		private final String registerId = UUID.randomUUID().toString();
		private final EventMatcher matcher;
		private final Listener listener;

		public ListenerWrapper(final EventMatcher matcher, final Listener listener) {
			this.matcher = matcher;
			this.listener = listener;
		}

		public String getRegisterId() {
			return registerId;
		}

		public EventMatcher getMatcher() {
			return matcher;
		}

		public Listener getListener() {
			return listener;
		}

		@Override
		public int hashCode() {
			return registerId.hashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			return obj instanceof ListenerWrapper && Objects.equals(((ListenerWrapper) obj).registerId, registerId);
		}
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class DispatcherModel extends ComponentModel {

		// Listeners that have EventType and Qualifier
		private Map<EventMatcher, List<ListenerWrapper>> listenersByMatcher;

		// Listeners that only have a match to EventType
		private Map<EventType, List<ListenerWrapper>> listenersByType;

		// Listeners that only match to the qualifier
		private Map<String, List<ListenerWrapper>> listenersByQualifiers;

		// Listeners by ID
		private Map<String, ListenerWrapper> listenersById;

		private Queue<Event> queuedEvents;

		private boolean dispatching;
	}

	public static class RegisterEvent extends Event<ListenerWrapper> {

		public RegisterEvent(final ListenerWrapper listener) {
			super(null, DispatcherEvent.REGISTER, listener);
		}
	}

	public static class UnRegisterEvent extends Event<String> {

		public UnRegisterEvent(final String registerId) {
			super(null, DispatcherEvent.UNREGISTER, registerId);
		}
	}

}
