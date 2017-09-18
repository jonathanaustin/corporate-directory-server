package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
import java.util.Collection;

/**
 * Items are selectable in this view.
 *
 * @param <T> the Item type
 * @param <C> the Collection type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectableView<T, C extends Collection<T>> extends CollectionView<T, C> {

	SelectMode getSelectMode();

	void setSelectMode(final SelectMode mode);

	boolean isViewMode();

}
