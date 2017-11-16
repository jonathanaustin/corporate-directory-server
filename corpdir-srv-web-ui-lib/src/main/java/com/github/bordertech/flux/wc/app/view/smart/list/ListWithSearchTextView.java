package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.wc.app.view.ListView;
import com.github.bordertech.flux.wc.app.view.search.SearchTextView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class ListWithSearchTextView<T> extends DefaultListWithSearchView<String, T> {

	public ListWithSearchTextView(final String viewId, final ListView<T> listView) {
		super(viewId, new SearchTextView("vw-srch"), listView);
	}
}
