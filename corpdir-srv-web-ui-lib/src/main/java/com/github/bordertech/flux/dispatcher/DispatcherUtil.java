package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.key.EventKey;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.key.StoreKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DispatcherUtil {

	/**
	 * A qualifier must start with a letter and followed by letters, digits or dash.
	 */
	public static final String QUALIFIER_VALIDATION_PATTERN = "[a-zA-Z][0-9a-zA-Z-]*";

	private static final Pattern QUALIFIER_PATTERN = Pattern.compile(QUALIFIER_VALIDATION_PATTERN);

	private DispatcherUtil() {
	}

	public static boolean validateQualifier(final String qualifier) {
		if (qualifier != null) {
			// Must start with a letter and followed by letters, digits and or underscores
			java.util.regex.Matcher matcher = QUALIFIER_PATTERN.matcher(qualifier);
			if (!matcher.matches()) {
				throw new IllegalArgumentException(
						"Qualifier ["
						+ qualifier
						+ "] must start with a letter and followed by letters, digits and or dash.");
			}
		}
		return true;
	}

	public static void registerDispatcherListener(final DispatcherEventType eventType, final DispatcherModel model, final Listener listener) {
		ListenerWrapper wrapper = new ListenerWrapper(new EventKey(eventType), listener);
		handleRegisterListener(wrapper, model);
	}

	public static ListenerWrapper getListener(final String registerId, final DispatcherModel model) {
		return model.getListenersById().get(registerId);
	}

	public static void handleRegisterListener(final ListenerWrapper wrapper, final DispatcherModel model) {
		// Register via the wrapper ID
		model.getListenersById().put(wrapper.getRegisterId(), wrapper);
		EventKey matcher = wrapper.getEventKey();
		// Register by the matcher details
		if (matcher.getType() != null && matcher.getQualifier() != null) {
			registerListenerInMap(model.getListenersByKey(), wrapper.getEventKey(), wrapper);
		} else if (matcher.getType() != null) {
			registerListenerInMap(model.getListenersByType(), matcher.getType(), wrapper);
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
		// Unregister by the matcher details
		EventKey matcher = wrapper.getEventKey();
		if (matcher.getType() != null && matcher.getQualifier() != null) {
			unregisterListenerFromMap(model.getListenersByKey(), wrapper.getEventKey(), wrapper);
		} else if (matcher.getType() != null) {
			unregisterListenerFromMap(model.getListenersByType(), matcher.getType(), wrapper);
		} else {
			unregisterListenerFromMap(model.getListenersByQualifiers(), matcher.getQualifier(), wrapper);
		}
	}

	public static void dispatch(final Event event, final DispatcherModel model) {
		model.getQueuedEvents().add(event);
		if (!model.isDispatching()) {
			processQueue(model);
		}
	}

	public static Store getStore(final StoreKey storeKey, final DispatcherModel model) {
		return model.getStoresByKey().get(storeKey);
	}

	public static void handleRegisterStore(final Store store, final DispatcherModel model) {
		// Just in case Unregister the previous store with this key
		handleUnregisterStore(store.getKey(), model);
		// Register the store
		model.getStoresByKey().put(store.getKey(), store);
		store.registerListeners();
	}

	public static void handleUnregisterStore(final StoreKey storeKey, final DispatcherModel model) {
		Store store = model.getStoresByKey().remove(storeKey);
		if (store != null) {
			store.unregisterListeners();
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
		} finally {
			model.setDispatching(false);
		}
	}

	private static void notifyListeners(final Event event, final DispatcherModel model) {
		List<ListenerWrapper> matches = getListenersForEvent(event.getKey(), model);
		for (ListenerWrapper wrapper : matches) {
			wrapper.getListener().handleEvent(event);
		}
	}

	private static List<ListenerWrapper> getListenersForEvent(final EventKey eventKey, final DispatcherModel model) {
		List<ListenerWrapper> matches = new ArrayList<>();
		// Check listeners only matching on Type
		matches.addAll(getListenersFromMap(model.getListenersByType(), eventKey.getType()));
		if (eventKey.getQualifier() != null) {
			// Check listeners with Type and Qualifier
			EventKey matcher = new EventKey(eventKey.getType(), eventKey.getQualifier());
			matches.addAll(getListenersFromMap(model.getListenersByKey(), matcher));
			// Check listeners only matching on Qualifier
			matches.addAll(getListenersFromMap(model.getListenersByQualifiers(), eventKey.getQualifier()));
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

	private static <T> void unregisterListenerFromMap(final Map<T, List<ListenerWrapper>> map, final T key, final ListenerWrapper listener) {
		if (map != null && key != null) {
			List<ListenerWrapper> listeners = map.get(key);
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty()) {
					map.remove(key);
				}
			}
		}
	}

}
