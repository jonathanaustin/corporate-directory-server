package com.github.bordertech.wcomponents.lib.app.view.input;

/**
 * Single selectable.
 *
 * @param <T> the option type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SingleSelectable<T> {

	T getSelectedOption();

	void setSelectedOption(final T option);

	void clearSelectedOption();

}
