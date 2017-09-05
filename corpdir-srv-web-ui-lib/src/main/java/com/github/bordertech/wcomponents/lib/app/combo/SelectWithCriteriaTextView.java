package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class SelectWithCriteriaTextView<T> extends SelectWithCriteriaView<String, T> {

	public SelectWithCriteriaTextView() {
		this(null);
	}

	public SelectWithCriteriaTextView(final String qualifier) {
		this(new SelectMenuView<T>(), qualifier);
	}

	public SelectWithCriteriaTextView(final SelectView<T> listView, final String qualifier) {
		super(new CriteriaTextView(), listView, qualifier);
	}
}
