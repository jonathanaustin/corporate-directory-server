package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import java.util.Collections;
import java.util.List;

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
		return current == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(current);
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
}
