package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.mode.ListMode;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 *
 * @author jonathan
 */
public class SelectWithCriteriaView<S, T> extends ListWithCriteriaView<S, T> implements SelectView<T> {

	public SelectWithCriteriaView(final Dispatcher dispatcher, final String qualifier, final CriteriaView<S> criteriaView, final SelectView<T> listView) {
		super(dispatcher, qualifier, criteriaView, listView);
	}

	@Override
	public SelectView<T> getListView() {
		return (SelectView<T>) super.getListView();
	}

	@Override
	public ListMode getListMode() {
		return getListView().getListMode();
	}

	@Override
	public void setListMode(ListMode mode) {
		getListView().setListMode(mode);
	}

	@Override
	public void clearSelectedIdx() {
		getListView().clearSelectedIdx();
	}

	@Override
	public void setSelectedIdx(int idx) {
		getListView().setSelectedIdx(idx);
	}

	@Override
	public int getSelectedIdx() {
		return getListView().getSelectedIdx();
	}

	@Override
	public int getSize() {
		return getListView().getSize();
	}

	@Override
	public T getSelected() {
		return getListView().getSelected();
	}

	@Override
	public void setSelected(final T entity) {
		getListView().setSelected(entity);
	}

}
