package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.action.base.ListBaseActionType;
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

	public DefaultListStore(final String storeKey) {
		super(storeKey);
	}

	@Override
	public List<T> getItems() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public void registerListeners() {
		// LIST Listeners
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
				handleAddItemAction((T) action.getData());
				break;
			case REMOVE_ITEM:
				handleRemoveItemAction((T) action.getData());
				break;
			case UPDATE_ITEM:
				handleUpdateItemAction((T) action.getData());
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

	protected void handleAddItemAction(final T item) {
		items.add(item);
	}

	protected void handleRemoveItemAction(final T item) {
		items.remove(item);
	}

	protected void handleUpdateItemAction(final T item) {
		int idx = items.indexOf(item);
		if (idx > -1) {
			items.remove(item);
			items.add(idx, item);
		} else {
			items.add(item);
		}
	}

	protected void handleLoadItemsAction(final Object data) {
		items.clear();
		if (data instanceof Collection) {
			items.addAll((Collection) data);
		} else if (data instanceof Object[]) {
			items.addAll(Arrays.asList((T[]) data));
		}
	}
}
