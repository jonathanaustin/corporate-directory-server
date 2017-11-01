package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.CollectionEventType;
import com.github.bordertech.wcomponents.lib.app.view.CollectionView;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.wc.DefaultStore;
import com.github.bordertech.wcomponents.lib.app.msg.MessageEventType;
import java.util.Collection;

/**
 * Controller for a Collections View.
 *
 * @author jonathan
 * @param <T> the item type
 * @param <C> the collection type
 */
public class CollectionMainCtrl<T, C extends Collection<T>> extends DefaultStore {

	@Override
	public void setupController() {
		super.setupController();

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

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getCollectionView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
	}

	public final CollectionView<T, C> getCollectionView() {
		return getComponentModel().collectionView;
	}

	public final void setCollectionView(final CollectionView<T, C> collectionView) {
		getOrCreateComponentModel().collectionView = collectionView;
		addView(collectionView);
	}

	protected void handleListEvents(final Event event) {
		CollectionEventType type = (CollectionEventType) event.getEventKey().getEventType();
		switch (type) {
			case RESET_COLLECTION:
				handleResetCollectionEvent();
				break;
			case LOAD_ITEMS:
				C items = (C) event.getData();
				handleLoadItems(items);
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
		}

	}

	protected void handleResetCollectionEvent() {
		getCollectionView().resetView();
	}

	protected void handleAddItemEvent(final T item) {
		getCollectionView().addItem(item);
		getCollectionView().showView(true);
	}

	protected void handleRemoveItemEvent(final T item) {
		CollectionView<T, C> colView = getCollectionView();
		colView.removeItem(item);
		if (colView.getViewBean().isEmpty()) {
			colView.showView(false);
		}
	}

	protected void handleUpdateItemEvent(final T item) {
		getCollectionView().updateItem(item);
	}

	protected void handleLoadItems(final C items) {
		if (items == null || items.isEmpty()) {
			dispatchMessage(MessageEventType.INFO, "No records found");
		} else {
			CollectionView<T, C> colView = getCollectionView();
			colView.setViewBean(items);
			colView.setContentVisible(true);
		}
	}

	@Override
	protected CollectionMainModel<T, C> newComponentModel() {
		return new CollectionMainModel();
	}

	@Override
	protected CollectionMainModel<T, C> getComponentModel() {
		return (CollectionMainModel) super.getComponentModel();
	}

	@Override
	protected CollectionMainModel<T, C> getOrCreateComponentModel() {
		return (CollectionMainModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the view.
	 */
	public static class CollectionMainModel<T, C extends Collection<T>> extends CtrlModel {

		private CollectionView<T, C> collectionView;
	}

}
