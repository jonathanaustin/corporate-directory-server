package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.CriteriaTextView;
import com.github.bordertech.wcomponents.lib.app.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 * Select View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class SelectWithCriteriaTextView<T> extends SelectWithCriteriaView<String, T> {

	public SelectWithCriteriaTextView(final Dispatcher dispatcher, final String qualifier) {
		this(dispatcher, qualifier, new SelectMenuView<T>(dispatcher, qualifier));
	}

	public SelectWithCriteriaTextView(final Dispatcher dispatcher, final String qualifier, final SelectView<T> listView) {
		super(dispatcher, qualifier, new CriteriaTextView(dispatcher, qualifier), listView);
	}
}
