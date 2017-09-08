package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class ListWithCriteriaTextView<T> extends ListWithCriteriaView<String, T> {

	public ListWithCriteriaTextView(final ListView<T> listView) {
		this(listView, null);
	}

	public ListWithCriteriaTextView(final ListView<T> listView, final String qualifier) {
		super(new CriteriaTextView(qualifier), listView, qualifier);
	}
}
