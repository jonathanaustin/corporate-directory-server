package com.github.bordertech.wcomponents.lib.app.view.combo.select;

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
public class SelectWithSearchTextView<T, C extends Collection<T>> extends SelectWithSearchView<String, T, C> {

	public SelectWithSearchTextView() {
		this((SelectSingleView<T, C>) new MenuSelectView<T>());
	}

	public SelectWithSearchTextView(final SelectSingleView<T, C> selectSingleView) {
		super(new SearchTextView(), selectSingleView);
	}
}
