package com.github.bordertech.wcomponents.lib.app.view.input;

import com.github.bordertech.wcomponents.lib.app.view.FormUpdateable;
import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Single select view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface InputSingleSelectView<T> extends View<T>, FormUpdateable {

	T getSelectedItem();

	void setSelectedItem(final T item);

	void clearSelectedItem();

}
