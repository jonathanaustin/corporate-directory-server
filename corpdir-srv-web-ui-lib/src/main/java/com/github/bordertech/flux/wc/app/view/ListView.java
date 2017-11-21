package com.github.bordertech.flux.wc.app.view;

import java.util.List;
import com.github.bordertech.flux.wc.view.FluxDumbView;

/**
 * List view.
 *
 * @param <T> the item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ListView<T> extends FluxDumbView<List<T>> {

	void setItems(final List<T> items);

	List<T> getItems();

	void refreshItems(final List<T> items);

	void addItem(final T item);

	void removeItem(final T item);

	void updateItem(final T item);
}
