package com.github.bordertech.wcomponents.lib.app.view;

import java.util.Collection;
import java.util.List;

/**
 * Multi select view.
 *
 * @param <T> the Item type
 * @param <C> the Collection type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectMultiView<T, C extends Collection<T>> extends SelectableView<T, C> {

	List<T> getSelectedItems();

	void setSelectedItems(final List<T> items);

	void clearSelected();

}
