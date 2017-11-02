package com.github.bordertech.wcomponents.lib.app.view.smart.list;

import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.list.MenuSelectView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the item type
 */
public class SelectWithSearchTextView<T> extends SelectWithSearchView<String, T> {

	public SelectWithSearchTextView(final String viewId) {
		this(viewId, (SelectSingleView<T>) new MenuSelectView<T>("vw-select"));
	}

	public SelectWithSearchTextView(final String viewId, final SelectSingleView<T> selectSingleView) {
		super(viewId, new SearchTextView("vw-srch"), selectSingleView);
	}
}
