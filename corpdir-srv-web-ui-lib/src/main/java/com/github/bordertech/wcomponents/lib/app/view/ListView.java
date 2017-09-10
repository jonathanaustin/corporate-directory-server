package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.List;

/**
 * List view.
 *
 * @param <T> the item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ListView<T> extends View<List<T>> {

	void addItem(final T item);

	void removeItem(final T item);

	void updateItem(final T item);

	void showList(final boolean show);

	int getSize();

}
