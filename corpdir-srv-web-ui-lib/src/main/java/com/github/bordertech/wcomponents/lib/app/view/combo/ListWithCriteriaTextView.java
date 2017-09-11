package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class ListWithCriteriaTextView<T> extends ListWithCriteriaView<String, T> {

	public ListWithCriteriaTextView(final ListView<T> listView) {
		super(new SearchTextView(), listView);
	}
}
