package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.search.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class ListWithCriteriaTextView<T> extends ListWithCriteriaView<String, T> {

	public ListWithCriteriaTextView(final ListView<T> listView) {
		super(new CriteriaTextView(), listView);
	}
}
