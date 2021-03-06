package com.github.bordertech.flux.wc.view.smart.select;

import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.search.SearchTextView;
import com.github.bordertech.flux.wc.view.dumb.select.MenuSelectView;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the item type
 */
public class SelectListWithSearchTextView<T> extends DefaultSelectListWithSearchView<String, T> {

	public SelectListWithSearchTextView(final String viewId) {
		this(viewId, (SelectSingleView<T>) new MenuSelectView<T>("vw_select"));
	}

	public SelectListWithSearchTextView(final String viewId, final SelectSingleView<T> selectSingleView) {
		super(viewId, new SearchTextView("vw_srch"), selectSingleView);
	}
}
