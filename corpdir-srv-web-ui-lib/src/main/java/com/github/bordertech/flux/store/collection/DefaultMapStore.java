package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.event.base.CollectionBaseEventType;
import com.github.bordertech.flux.key.StoreKey;
import com.github.bordertech.flux.store.DefaultStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Map store.
 *
 * @param <K> the key type
 * @param <V> the entry type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultMapStore<K, V> extends DefaultStore implements MapStore<K, V> {

	private final Map<K, V> items = new HashMap<>();

	public DefaultMapStore(final StoreKey storeKey) {
		super(storeKey);
	}

	@Override
	public void registerListeners() {
		// Collection Listeners
		for (CollectionBaseEventType type : CollectionBaseEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleListEvents(event);
				}
			};
			registerListener(type, listener);
		}
	}

	@Override
	public V getValue(final K key) {
		return items.get(key);
	}

	protected void handleListEvents(final Event event) {
		CollectionBaseEventType type = (CollectionBaseEventType) event.getKey().getType();
		boolean handled = true;
		switch (type) {
			case RESET_ITEMS:
				handleResetItemsEvent();
				break;
			case LOAD_ITEMS:
				handleLoadItemsEvent(event.getData());
				break;
			case ADD_ITEM:
				handleAddItemEvent((Map.Entry<K, V>) event.getData());
				break;
			case REMOVE_ITEM:
				handleRemoveItemEvent((Map.Entry<K, V>) event.getData());
				break;
			case UPDATE_ITEM:
				handleUpdateItemEvent((Map.Entry<K, V>) event.getData());
				break;

			default:
				handled = false;
		}
		if (handled) {
			dispatchChangeEvent(type);
		}

	}

	protected void handleResetItemsEvent() {
		items.clear();
	}

	protected void handleAddItemEvent(final Map.Entry<K, V> item) {
		items.put(item.getKey(), item.getValue());
	}

	protected void handleRemoveItemEvent(final Map.Entry<K, V> item) {
		items.remove(item.getKey());
	}

	protected void handleUpdateItemEvent(final Map.Entry<K, V> item) {
		items.put(item.getKey(), item.getValue());
	}

	protected void handleLoadItemsEvent(final Object data) {
		items.clear();
		if (data instanceof Map) {
			items.putAll((Map<K, V>) data);
		} else if (data instanceof Collection) {
			for (Object item : (Collection) data) {
				if (item instanceof Map.Entry) {
					Map.Entry<K, V> entry = (Map.Entry) item;
					items.put(entry.getKey(), entry.getValue());
				}
			}
		} else if (data instanceof Object[]) {
			Object[] entries = (Object[]) data;
			for (Object item : entries) {
				if (item instanceof Map.Entry) {
					Map.Entry<K, V> entry = (Map.Entry) item;
					items.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}
}
