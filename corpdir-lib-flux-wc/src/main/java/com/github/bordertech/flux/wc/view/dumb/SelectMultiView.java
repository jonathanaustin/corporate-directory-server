package com.github.bordertech.flux.wc.view.dumb;

import java.util.List;

/**
 * Multi select view.
 *
 * @param <T> the Item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectMultiView<T> extends SelectableView<T> {

	List<T> getSelectedItems();

	void setSelectedItems(final List<T> items);

	void clearSelected();

}
