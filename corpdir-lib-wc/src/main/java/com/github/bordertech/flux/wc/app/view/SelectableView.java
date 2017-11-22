package com.github.bordertech.flux.wc.app.view;

import com.github.bordertech.flux.wc.app.mode.SelectMode;

/**
 * Items are selectable in this view.
 *
 * @param <T> the Item type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SelectableView<T> extends ListView<T> {

	SelectMode getSelectMode();

	void setSelectMode(final SelectMode mode);

	boolean isViewMode();

}
