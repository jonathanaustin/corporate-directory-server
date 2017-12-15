package com.github.bordertech.flux.wc.view.dumb.list;

import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.dumb.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Basic list view.
 *
 * @author Jonathan Austin
 * @param <T> the item type
 * @since 1.0.0
 */
public class AbstractListView<T> extends DefaultDumbView<List<T>> implements ListView<T> {

	public AbstractListView(final String viewId) {
		super(viewId);
	}

	@Override
	public List<T> getItems() {
		List<T> current = getViewBean();
		if (current == null) {
			current = new ArrayList();
			if (getBeanProperty() != null && !Objects.equals(getBeanProperty(), ".")) {
				throw new IllegalStateException("Setting a bean value when a bean property has been specified.");
			}
			setViewBean(current);
		}
		return current;
	}

	@Override
	public void setItems(final List<T> items) {
		setViewBean(items);
	}

	@Override
	public void refreshItems(final List<T> items) {
		boolean wasVisible = isContentVisible();
		resetContent();
		setItems(items);
		setContentVisible(wasVisible);
	}

	@Override
	public void addItem(final T item) {
		List<T> items = getItems();
		if (!items.contains(item)) {
			items.add(item);
//			items = new ArrayList<>(items);
//			items.add(item);
//			refreshItems(items);
		}
	}

	@Override
	public void removeItem(final T item) {
		List<T> items = getItems();
		if (items.contains(item)) {
			items.remove(item);
//			items = new ArrayList<>(items);
//			items.remove(item);
//			refreshItems(items);
		}
	}

	@Override
	public void updateItem(final T item) {
		List<T> items = getItems();
		int idx = items.indexOf(item);
		if (idx > -1) {
			items.remove(item);
			items.add(idx, item);
//			items = new ArrayList<>(items);
//			items.remove(item);
//			items.add(item);
//			refreshItems(items);
		}
	}
}
