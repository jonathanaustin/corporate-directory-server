package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.dumb.list.MenuSelectView;
import com.github.bordertech.flux.wc.app.view.dumb.search.SearchTextView;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the item type
 */
public class SelectListWithSearchTextView<T> extends DefaultSelectListWithSearchView<String, T> {

	public SelectListWithSearchTextView(final String viewId) {
		this(viewId, (SelectSingleView<T>) new MenuSelectView<T>("vw-select"));
	}

	public SelectListWithSearchTextView(final String viewId, final SelectSingleView<T> selectSingleView) {
		super(viewId, new SearchTextView("vw-srch"), selectSingleView);
	}
}
