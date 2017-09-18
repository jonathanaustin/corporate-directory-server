package com.github.bordertech.wcomponents.lib.app.view.input;

import com.github.bordertech.wcomponents.AbstractWMultiSelectList;

/**
 * Multi select options view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface MultiSelectOptionsView<T> extends InputMultiSelectView<T>, InputOptionsView<T> {

	@Override
	AbstractWMultiSelectList getSelectInput();

}
