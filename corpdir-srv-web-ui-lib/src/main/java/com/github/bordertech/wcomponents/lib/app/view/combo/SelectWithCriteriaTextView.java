package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import com.github.bordertech.wcomponents.lib.app.view.list.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class SelectWithCriteriaTextView<T> extends SelectWithCriteriaView<String, T> {

	public SelectWithCriteriaTextView() {
		this(new SelectMenuView<T>());
	}

	public SelectWithCriteriaTextView(final SelectView<T> listView) {
		super(new SearchTextView(), listView);
	}
}
