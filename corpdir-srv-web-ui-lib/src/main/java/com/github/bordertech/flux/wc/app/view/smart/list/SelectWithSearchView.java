package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.wc.app.view.smart.list.ListWithSearchView;
import com.github.bordertech.flux.wc.app.mode.SelectMode;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;

/**
 * Select view with a search view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class SelectWithSearchView<S, T> extends ListWithSearchView<S, T> implements SelectSingleView<T> {

	public SelectWithSearchView(final String viewId, final SearchView<S> criteriaView, final SelectSingleView<T> listView) {
		super(viewId, criteriaView, listView);
	}

	@Override
	public SelectSingleView<T> getListView() {
		return (SelectSingleView<T>) super.getListView();
	}

	@Override
	public SelectMode getSelectMode() {
		return getListView().getSelectMode();
	}

	@Override
	public void setSelectMode(SelectMode mode) {
		getListView().setSelectMode(mode);
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
	public boolean isViewMode() {
		return getListView().isViewMode();
	}

//	protected void handleSearchEvents(final Event event) {
//		SearchViewEvent type = (SearchViewEvent) event.getEventKey().getEventType();
//		switch (type) {
//			case SEARCH_VALIDATING:
//				dispatchEvent(CollectionEventType.RESET_COLLECTION);
//				dispatchEvent(PollingViewEvent.RESET_POLLING);
//				break;
//			case SEARCH:
//				dispatchEvent(PollingViewEvent.START_POLLING, event.getData());
//				break;
//		}
//	}
}
