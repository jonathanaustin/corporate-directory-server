package com.github.bordertech.wcomponents.lib.app.store;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.wc.DefaultStore;
import com.github.bordertech.wcomponents.lib.app.event.CollectionEventType;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection state.
 *
 * @param <T> the item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class CollectionStore<T> extends DefaultStore {

	private List<T> items;

	public CollectionStore() {
		this(null);
	}

	public CollectionStore(final String qualifier) {
		super(null, qualifier);
	}

	public List<T> getItems() {
		return items;
	}

	@Override
	public void registerListeners() {
		// LIST Listeners
		for (CollectionEventType type : CollectionEventType.values()) {
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
		CollectionEventType type = (CollectionEventType) event.getEventKey().getEventType();
		boolean handled = true;
		switch (type) {
			case RESET_COLLECTION:
				handleResetCollectionEvent();
				break;
//			case LOAD_ITEMS:
//				C items = (C) event.getData();
//				handleLoadItems(items);
//				break;
			case ADD_ITEM:
				handleAddItemEvent((T) event.getData());
				break;
			case REMOVE_ITEM:
//				handleRemoveItemEvent((T) event.getData());
				break;
			case UPDATE_ITEM:
//				handleUpdateItemEvent((T) event.getData());
				break;
			default:
				handled = false;
		}
		if (handled) {
			dispatchChangeEvent();
		}

	}

	protected void handleResetCollectionEvent() {
		items = null;
	}

	protected void handleAddItemEvent(final T item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}

//	protected void handleRemoveItemEvent(final T item) {
//		CollectionView<T, C> colView = getCollectionView();
//		colView.removeItem(item);
//		if (colView.getViewBean().isEmpty()) {
//			colView.showView(false);
//		}
//	}
//
//	protected void handleUpdateItemEvent(final T item) {
//		getCollectionView().updateItem(item);
//	}
//
//	protected void handleLoadItems(final C items) {
//		if (items == null || items.isEmpty()) {
//			dispatchMessage(MessageEventType.INFO, "No records found");
//		} else {
//			CollectionView<T, C> colView = getCollectionView();
//			colView.setViewBean(items);
//			colView.setContentVisible(true);
//		}
//	}
}
