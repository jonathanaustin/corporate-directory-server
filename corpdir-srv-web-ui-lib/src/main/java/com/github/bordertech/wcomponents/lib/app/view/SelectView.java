package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.ListMode;

/**
 * Entity select list view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectView<T> extends ListView<T>, FormUpdateable {

	ListMode getListMode();

	void setListMode(final ListMode mode);

	void clearSelected();

	T getSelectedItem();

	void setSelectedItem(final T item);

	int getIndexOfItem(final T item);

	T getItem(final int idx);

}
