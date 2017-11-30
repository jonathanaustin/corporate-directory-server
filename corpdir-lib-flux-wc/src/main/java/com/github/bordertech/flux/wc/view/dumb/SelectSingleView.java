package com.github.bordertech.flux.wc.view.dumb;

/**
 * Single select view.
 *
 * @param <T> the Item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectSingleView<T> extends SelectableView<T> {

	T getSelectedItem();

	void setSelectedItem(final T item);

	void clearSelected();

}
