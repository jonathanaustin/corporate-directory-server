package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.Collection;

/**
 * Collection view.
 *
 * @param <T> the item type
 * @param <C> the collection type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface CollectionView<T, C extends Collection<T>> extends View<C> {

	void addItem(final T item);

	void removeItem(final T item);

	void updateItem(final T item);

	void showView(final boolean show);

	int getSize();

	void setItems(final C items);

	C getItems();

	void refreshItems(final C items);

}
