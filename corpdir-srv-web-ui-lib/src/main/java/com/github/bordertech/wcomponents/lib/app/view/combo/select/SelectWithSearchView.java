package com.github.bordertech.wcomponents.lib.app.view.combo.select;

import com.github.bordertech.wcomponents.lib.app.view.combo.collection.CollectionWithSearchView;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingSearchCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import java.util.Collection;

/**
 * Select view with a search view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class SelectWithSearchView<S, T, C extends Collection<T>> extends CollectionWithSearchView<S, T, C> implements SelectSingleView<T, C> {

	public SelectWithSearchView(final SearchView<S> criteriaView, final SelectSingleView<T, C> listView) {
		super(criteriaView, listView);
		PollingSearchCtrl ctrl = new PollingSearchCtrl();
		ctrl.setPollingView(getPollingView());
		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-polsrch", ctrl);
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
	public void setSelectedItem(final T item) {
		getCollectionView().setSelectedItem(item);
	}

	@Override
	public boolean isViewMode() {
		return getCollectionView().isViewMode();
	}

}
