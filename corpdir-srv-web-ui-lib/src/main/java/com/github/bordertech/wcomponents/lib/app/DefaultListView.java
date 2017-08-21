package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.wc.DefaultViewBound;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class DefaultListView<T> extends DefaultViewBound<List<T>> implements ListView<T> {

	public DefaultListView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultListView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
	}

	@Override
	public void addItem(final T entity) {
		List<T> items = getItemList();
		items.add(entity);
		refreshItemList(items);
	}

	@Override
	public void removeItem(final T entity) {
		List<T> items = getItemList();
		items.remove(entity);
		refreshItemList(items);
	}

	@Override
	public void updateItem(final T entity) {
		List<T> items = getItemList();
		int idx = items.indexOf(entity);
		if (idx == -1) {
			throw new IllegalStateException("Could not update entity [" + entity.toString() + "].");
		}
		items.remove(idx);
		items.add(idx, entity);
		refreshItemList(items);
	}

	protected List<T> getItemList() {
		List<T> current = getViewBean();
		List<T> items = current == null ? new ArrayList<T>() : new ArrayList<T>(current);
		return items;
	}

	protected void refreshItemList(final List<T> items) {
		resetContent();
		setViewBean(items);
	}

}
