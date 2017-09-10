package com.github.bordertech.wcomponents.lib.app.list;

import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract list view.
 *
 * @author Jonathan Austin
 * @param <T> the item bean
 * @since 1.0.0
 */
public class DefaultListView<T> extends DefaultView<List<T>> implements ListView<T> {

	@Override
	public void addItem(final T item) {
		List<T> items = getItemList();
		if (!items.contains(item)) {
			items.add(item);
			refreshItemList(items);
		}
	}

	@Override
	public void removeItem(final T item) {
		List<T> items = getItemList();
		if (items.contains(item)) {
			items.remove(item);
			refreshItemList(items);
		}
	}

	@Override
	public void updateItem(final T item) {
		List<T> items = getItemList();
		int idx = items.indexOf(item);
		if (idx > -1) {
			items.remove(idx);
			items.add(idx, item);
		} else {
			items.add(item);
		}
		refreshItemList(items);
	}

	@Override
	public void showList(final boolean show) {
		setContentVisible(show);
	}

	@Override
	public int getSize() {
		List<T> items = getViewBean();
		return items == null ? 0 : items.size();
	}

	protected List<T> getItemList() {
		List<T> current = getViewBean();
		List<T> items = current == null ? new ArrayList<T>() : new ArrayList<T>(current);
		return items;
	}

	protected void refreshItemList(final List<T> items) {
		boolean wasVisible = isContentVisible();
		resetContent();
		setViewBean(items);
		setContentVisible(wasVisible);
	}

}
