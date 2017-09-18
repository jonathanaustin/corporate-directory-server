package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.list.MenuSelectView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import java.util.Collection;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the item type
 * @param <C> the collection type
 */
public class SelectWithCriteriaTextView<T, C extends Collection<T>> extends SelectWithCriteriaView<String, T, C> {

	public SelectWithCriteriaTextView() {
		this((SelectSingleView<T, C>) new MenuSelectView<T>());
	}

	public SelectWithCriteriaTextView(final SelectSingleView<T, C> selectSingleView) {
		super(new SearchTextView(), selectSingleView);
	}
}
