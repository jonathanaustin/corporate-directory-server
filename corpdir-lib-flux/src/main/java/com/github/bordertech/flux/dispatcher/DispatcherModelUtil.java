package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DispatcherActionType;
import com.github.bordertech.flux.key.ActionKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * DispatcherModel helper class.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DispatcherModelUtil {

	/**
	 * A qualifier must start with a letter and followed by letters, digits or dash.
	 */
	public static final String QUALIFIER_VALIDATION_PATTERN = "[a-zA-Z][0-9a-zA-Z-]*";

	private static final Pattern QUALIFIER_PATTERN = Pattern.compile(QUALIFIER_VALIDATION_PATTERN);

	private DispatcherModelUtil() {
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

	public static void registerDispatcherListener(final DispatcherActionType actionType, final DispatcherModel model, final Listener listener) {
		ListenerWrapper wrapper = new ListenerWrapper(new ActionKey(actionType), listener);
		handleRegisterListener(wrapper, model);
	}

	public static ListenerWrapper getListener(final String registerId, final DispatcherModel model) {
		return model.getListenersById().get(registerId);
	}

	public static void handleRegisterListener(final ListenerWrapper wrapper, final DispatcherModel model) {
		// Register via the wrapper ID
		model.getListenersById().put(wrapper.getRegisterId(), wrapper);
		ActionKey matcher = wrapper.getActionKey();
		// Register by the matcher details
		if (matcher.getType() != null && matcher.getQualifier() != null) {
			registerListenerInMap(model.getListenersByKey(), wrapper.getActionKey(), wrapper);
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
		ActionKey matcher = wrapper.getActionKey();
		if (matcher.getType() != null && matcher.getQualifier() != null) {
			unregisterListenerFromMap(model.getListenersByKey(), wrapper.getActionKey(), wrapper);
		} else if (matcher.getType() != null) {
			unregisterListenerFromMap(model.getListenersByType(), matcher.getType(), wrapper);
		} else {
			unregisterListenerFromMap(model.getListenersByQualifiers(), matcher.getQualifier(), wrapper);
		}
	}

	public static void dispatch(final Action action, final DispatcherModel model) {
		model.getQueuedActions().add(action);
		if (!model.isDispatching()) {
			processQueue(model);
		}
	}

	public static Store getStore(final String storeKey, final DispatcherModel model) {
		if (storeKey == null) {
			throw new IllegalArgumentException("Store key must be provided.");
		}
		Store store = model.getStoresByKey().get(storeKey);
		if (store == null) {
			throw new IllegalStateException("No Store registered for key [" + storeKey + "].");
		}
		return store;
	}

	public static void handleRegisterStore(final Store store, final DispatcherModel model) {
		// Just in case Unregister the previous store with this key
		handleUnregisterStore(store.getKey(), model);
		// Register the store
		model.getStoresByKey().put(store.getKey(), store);
		// Register the store listeners

		Set<String> ids = new HashSet<>();
		store.registerListeners(ids);
		if (!ids.isEmpty()) {
			model.getStoreRegisteredIds().put(store.getKey(), ids);
		}
	}

	public static void handleUnregisterStore(final String storeKey, final DispatcherModel model) {
		Store store = model.getStoresByKey().remove(storeKey);
		if (store != null) {
			// Unregister the Store Listeners
			Set<String> ids = model.getStoreRegisteredIds().remove(storeKey);
			if (ids != null) {
				for (String id : ids) {
					handleUnregisterListener(id, model);
				}
			}
		}
	}

	public static ActionCreator getActionCreator(final String creatorKey, final DispatcherModel model) {
		if (creatorKey == null) {
			throw new IllegalArgumentException("ActionCreator key must be provided.");
		}
		ActionCreator creator = model.getActionCreatorsByKey().get(creatorKey);
		if (creator == null) {
			throw new IllegalStateException("No ActionCreator registered for key [" + creatorKey + "].");
		}
		return creator;
	}

	public static void handleRegisterActionCreator(final ActionCreator creator, final DispatcherModel model) {
		model.getActionCreatorsByKey().put(creator.getKey(), creator);
	}

	public static void handleUnregisterActionCreator(final String creatorKey, final DispatcherModel model) {
		model.getActionCreatorsByKey().remove(creatorKey);
	}

	private static void processQueue(final DispatcherModel model) {
		model.setDispatching(true);
		try {
			Action next = model.getQueuedActions().poll();
			while (next != null) {
				notifyListeners(next, model);
				next = model.getQueuedActions().poll();
			}
		} finally {
			model.setDispatching(false);
		}
	}

	private static void notifyListeners(final Action action, final DispatcherModel model) {
		List<ListenerWrapper> matches = getListenersForAction(action.getKey(), model);
		for (ListenerWrapper wrapper : matches) {
			wrapper.getListener().handleAction(action);
		}
	}

	private static List<ListenerWrapper> getListenersForAction(final ActionKey actionKey, final DispatcherModel model) {
		List<ListenerWrapper> matches = new ArrayList<>();
		// Check listeners only matching on Type
		matches.addAll(getListenersFromMap(model.getListenersByType(), actionKey.getType()));
		if (actionKey.getQualifier() != null) {
			// Check listeners with Type and Qualifier
			ActionKey matcher = new ActionKey(actionKey.getType(), actionKey.getQualifier());
			matches.addAll(getListenersFromMap(model.getListenersByKey(), matcher));
			// Check listeners only matching on Qualifier
			matches.addAll(getListenersFromMap(model.getListenersByQualifiers(), actionKey.getQualifier()));
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
