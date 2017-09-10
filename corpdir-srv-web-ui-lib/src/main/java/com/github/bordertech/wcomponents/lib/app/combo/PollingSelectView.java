package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.lib.app.mode.ListMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;

/**
 * Select view with a criteria view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class PollingSelectView<S, T> extends PollingListView<S, T> implements SelectView<T> {

	public PollingSelectView(final SelectView<T> listView) {
		super(listView);
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
	public void clearSelected() {
		getListView().clearSelected();
	}

	@Override
	public T getSelectedItem() {
		return getListView().getSelectedItem();
	}

	@Override
	public void setSelectedItem(final T item) {
		getListView().setSelectedItem(item);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		getListView().doMakeFormReadonly(readonly);
	}

	@Override
	public int getIndexOfItem(final T item) {
		return getListView().getIndexOfItem(item);
	}

	@Override
	public T getItem(final int idx) {
		return getListView().getItem(idx);
	}

}
