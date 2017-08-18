package com.github.bordertech.wcomponents.lib.app.comp;

import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 *
 * @author jonathan
 */
public class ListWithCriteriaTextView<T> extends ListWithCriteriaView<String, T> {

	public ListWithCriteriaTextView(final Dispatcher dispatcher, final String qualifier, final ListView<T> listView) {
		super(dispatcher, qualifier, new CriteriaTextView(dispatcher, qualifier), listView);
	}
}
