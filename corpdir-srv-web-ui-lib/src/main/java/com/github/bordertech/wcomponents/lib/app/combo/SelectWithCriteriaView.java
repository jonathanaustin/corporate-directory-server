package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;

/**
 * Select view with a criteria view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class SelectWithCriteriaView<S, T> extends ListWithCriteriaView<S, T> {

	public SelectWithCriteriaView(final CriteriaView<S> criteriaView, final SelectView<T> listView) {
		this(criteriaView, listView, null);
	}

	public SelectWithCriteriaView(final CriteriaView<S> criteriaView, final SelectView<T> listView, final String qualifier) {
		super(criteriaView, listView, qualifier);
	}

	@Override
	public SelectView<T> getListView() {
		return (SelectView<T>) super.getListView();
	}

}
