package com.github.bordertech.wcomponents.lib.app.view.input;

import com.github.bordertech.wcomponents.lib.app.view.FormUpdateable;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.List;

/**
 * Multi select view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface InputMultiSelectView<T> extends View<T>, FormUpdateable {

	List<T> getSelectedItems();

	void setSelectedItems(final List<T> items);

	void addSelectedItem(final T item);

	void removeSelectedItem(final T item);

	void clearSelectedItems();

}
