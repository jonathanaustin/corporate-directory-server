package com.github.bordertech.wcomponents.lib.app.view;

import java.util.Collection;

/**
 * Single select view.
 *
 * @param <T> the Item type
 * @param <C> the Collection type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectSingleView<T, C extends Collection<T>> extends SelectableView<T, C> {

	T getSelectedItem();

	void setSelectedItem(final T item);

	void clearSelected();

}
