package com.github.bordertech.flux.store.impl;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.action.type.base.ListBaseActionType;
import com.github.bordertech.flux.store.MapStore;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Default Map store.
 *
 * @param <K> the key type
 * @param <V> the entry type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public abstract class AbstractMapStore<K, V> extends DefaultStore implements MapStore<K, V> {

	public AbstractMapStore(final String storeKey) {
		super(storeKey);
	}

	public AbstractMapStore(final String storeKey, final Set<String> actionCreatorKeys) {
		super(storeKey, actionCreatorKeys);
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		super.registerListeners(ids);
		// Collection Listeners
		for (ListBaseActionType type : ListBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleListActions(action);
				}
			};
			ids.addAll(registerActionCreatorListeners(type, listener));
		}
	}

	@Override
	public V getValue(final K key) {
		return getMap().get(key);
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
		getMap().clear();
	}

	protected void handleAddItemAction(final Map.Entry<K, V> item) {
		getMap().put(item.getKey(), item.getValue());
	}

	protected void handleRemoveItemAction(final Map.Entry<K, V> item) {
		getMap().remove(item.getKey());
	}

	protected void handleUpdateItemAction(final Map.Entry<K, V> item) {
		getMap().put(item.getKey(), item.getValue());
	}

	protected void handleLoadItemsAction(final Object data) {
		getMap().clear();
		if (data instanceof Map) {
			getMap().putAll((Map<K, V>) data);
		} else if (data instanceof Collection) {
			for (Object item : (Collection) data) {
				if (item instanceof Map.Entry) {
					Map.Entry<K, V> entry = (Map.Entry) item;
					getMap().put(entry.getKey(), entry.getValue());
				}
			}
		} else if (data instanceof Object[]) {
			Object[] entries = (Object[]) data;
			for (Object item : entries) {
				if (item instanceof Map.Entry) {
					Map.Entry<K, V> entry = (Map.Entry) item;
					getMap().put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

}
