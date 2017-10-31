package com.github.bordertech.wcomponents.lib.app.view.collection;

import com.github.bordertech.wcomponents.lib.app.view.CollectionView;
import com.github.bordertech.flux.wc.view.DefaultAppView;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract Collection view.
 *
 * @param <T> the Item type
 * @param <C> the Collection Type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractCollectionView<T, C extends Collection<T>> extends DefaultAppView<C> implements CollectionView<T, C> {

	@Override
	public void addItem(final T item) {
		C items = getItems();
		if (!items.contains(item)) {
			items.add(item);
			refreshItems(items);
		}
	}

	@Override
	public void removeItem(final T item) {
		C items = getItems();
		if (items.contains(item)) {
			items.remove(item);
			refreshItems(items);
		}
	}

	@Override
	public void updateItem(final T item) {
		C items = getItems();
		items.remove(item);
		items.add(item);
		refreshItems(items);
	}

	@Override
	public void showView(final boolean show) {
		setContentVisible(show);
	}

	@Override
	public int getSize() {
		Collection<T> items = getViewBean();
		return items == null ? 0 : items.size();
	}

	@Override
	public C getItems() {
		C current = getViewBean();
		if (current == null) {
			return (C) new ArrayList<>();
		}
		return (C) new ArrayList<>(current);
	}

	@Override
	public void setItems(final C items) {
		setViewBean(items);
	}

	@Override
	public void refreshItems(final C items) {
		boolean wasVisible = isContentVisible();
		resetContent();
		setItems(items);
		setContentVisible(wasVisible);
	}

}
