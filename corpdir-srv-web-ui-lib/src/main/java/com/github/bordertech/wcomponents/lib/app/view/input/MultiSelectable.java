package com.github.bordertech.wcomponents.lib.app.view.input;

import java.util.List;

/**
 * Multi selectable.
 *
 * @param <T> the option type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface MultiSelectable<T> {

	List<T> getSelectedOptions();

	void setSelectedOptions(final List<T> options);

	void addSelectedOption(final T option);

	void removeSelectedOption(final T option);

	void clearSelectedOptions();

}
