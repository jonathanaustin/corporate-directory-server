package com.github.bordertech.flux.wc.view.smart.list;

import com.github.bordertech.flux.wc.view.dumb.ListView;
import com.github.bordertech.flux.wc.view.dumb.search.SearchTextView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class ListWithSearchTextView<T> extends DefaultListWithSearchView<String, T> {

	public ListWithSearchTextView(final String viewId, final ListView<T> listView) {
		super(viewId, new SearchTextView("vw_srch"), listView);
	}
}
