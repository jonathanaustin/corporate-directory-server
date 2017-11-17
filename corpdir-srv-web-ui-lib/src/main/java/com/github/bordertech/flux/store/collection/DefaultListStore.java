package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.event.base.CollectionBaseEventType;
import com.github.bordertech.flux.key.StoreKey;
import com.github.bordertech.flux.store.DefaultStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * List store.
 *
 * @param <T> the item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultListStore<T> extends DefaultStore implements ListStore<T> {

	private final List<T> items = new ArrayList<>();

	public DefaultListStore(final StoreKey storeKey) {
		super(storeKey);
	}

	@Override
	public List<T> getItems() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public void registerListeners() {
		// LIST Listeners
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
				handleAddItemEvent((T) event.getData());
				break;
			case REMOVE_ITEM:
				handleRemoveItemEvent((T) event.getData());
				break;
			case UPDATE_ITEM:
				handleUpdateItemEvent((T) event.getData());
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

	protected void handleAddItemEvent(final T item) {
		items.add(item);
	}

	protected void handleRemoveItemEvent(final T item) {
		items.remove(item);
	}

	protected void handleUpdateItemEvent(final T item) {
		int idx = items.indexOf(item);
		if (idx > -1) {
			items.remove(item);
			items.add(idx, item);
		} else {
			items.add(item);
		}
	}

	protected void handleLoadItemsEvent(final Object data) {
		items.clear();
		if (data instanceof Collection) {
			items.addAll((Collection) data);
		} else if (data instanceof Object[]) {
			items.addAll(Arrays.asList((T[]) data));
		}
	}
}
