package com.github.bordertech.wcomponents.lib.app.view.combo.select;

import com.github.bordertech.wcomponents.lib.app.view.combo.list.PollingListView;
import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import java.util.Collection;

/**
 * Polling view and Select Single View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class PollingSelectView<S, T, C extends Collection<T>> extends PollingListView<S, T, C> implements SelectSingleView<T, C> {

	public PollingSelectView(final SelectSingleView<T, C> listView) {
		super(listView);
	}

	@Override
	public SelectSingleView<T, C> getCollectionView() {
		return (SelectSingleView<T, C>) super.getCollectionView();
	}

	@Override
	public SelectMode getSelectMode() {
		return getCollectionView().getSelectMode();
	}

	@Override
	public void setSelectMode(SelectMode mode) {
		getCollectionView().setSelectMode(mode);
	}

	@Override
	public void clearSelected() {
		getCollectionView().clearSelected();
	}

	@Override
	public T getSelectedItem() {
		return getCollectionView().getSelectedItem();
	}

	@Override
	public void setSelectedItem(T item) {
		getCollectionView().setSelectedItem(item);
	}

	@Override
	public boolean isViewMode() {
		return getCollectionView().isViewMode();
	}

}
