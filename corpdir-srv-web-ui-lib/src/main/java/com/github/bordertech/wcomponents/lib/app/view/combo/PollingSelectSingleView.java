package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import java.util.Collection;

/**
 * Select view with a criteria view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class PollingSelectSingleView<S, T, C extends Collection<T>> extends PollingCollectionView<S, T, C> implements SelectSingleView<T, C> {

	public PollingSelectSingleView(final SelectSingleView<T, C> listView) {
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
