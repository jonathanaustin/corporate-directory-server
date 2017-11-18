package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.action.base.ListBaseActionType;
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

	public DefaultMapStore(final String storeKey) {
		super(storeKey);
	}

	@Override
	public void registerListeners() {
		// Collection Listeners
		for (ListBaseActionType type : ListBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleListActions(action);
				}
			};
			registerListener(type, listener);
		}
	}

	@Override
	public V getValue(final K key) {
		return items.get(key);
	}

	protected void handleListActions(final Action action) {
		ListBaseActionType type = (ListBaseActionType) action.getKey().getType();
		boolean handled = true;
		switch (type) {
			case RESET_ITEMS:
				handleResetItemsAction();
				break;
			case LOAD_ITEMS:
				handleLoadItemsAction(action.getData());
				break;
			case ADD_ITEM:
				handleAddItemAction((Map.Entry<K, V>) action.getData());
				break;
			case REMOVE_ITEM:
				handleRemoveItemAction((Map.Entry<K, V>) action.getData());
				break;
			case UPDATE_ITEM:
				handleUpdateItemAction((Map.Entry<K, V>) action.getData());
				break;

			default:
				handled = false;
		}
		if (handled) {
			dispatchChangeAction(type);
		}

	}

	protected void handleResetItemsAction() {
		items.clear();
	}

	protected void handleAddItemAction(final Map.Entry<K, V> item) {
		items.put(item.getKey(), item.getValue());
	}

	protected void handleRemoveItemAction(final Map.Entry<K, V> item) {
		items.remove(item.getKey());
	}

	protected void handleUpdateItemAction(final Map.Entry<K, V> item) {
		items.put(item.getKey(), item.getValue());
	}

	protected void handleLoadItemsAction(final Object data) {
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
