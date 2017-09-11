package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;

/**
 * Entity select list view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectView<T> extends ListView<T>, FormUpdateable {

	SelectMode getListMode();

	void setListMode(final SelectMode mode);

	void clearSelected();

	T getSelectedItem();

	void setSelectedItem(final T item);

}
