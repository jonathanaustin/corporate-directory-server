package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class ListWithCriteriaTextView<T> extends ListWithCriteriaView<String, T> {

	public ListWithCriteriaTextView(final Dispatcher dispatcher, final String qualifier, final ListView<T> listView) {
		super(dispatcher, qualifier, new CriteriaTextView(dispatcher, qualifier), listView);
	}
}
