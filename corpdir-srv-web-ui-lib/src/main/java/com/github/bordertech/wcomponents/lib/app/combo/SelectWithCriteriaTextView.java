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
		this(new SelectMenuView<T>());
	}

	public SelectWithCriteriaTextView(final SelectView<T> listView) {
		super(new CriteriaTextView(), listView);
	}
}
