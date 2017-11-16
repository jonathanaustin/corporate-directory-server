package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.SelectMode;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.event.base.SelectableBaseViewEvent;

/**
 * Select view with a search view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class DefaultSelectListWithSearchView<S, T> extends DefaultListWithSearchView<S, T> implements SelectSingleView<T> {

	public DefaultSelectListWithSearchView(final String viewId, final SearchView<S> criteriaView, final SelectSingleView<T> listView) {
		super(viewId, criteriaView, listView);
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof SelectableBaseViewEvent) {
			handleSelectableBaseEvents((SelectableBaseViewEvent) event, data);
		}
	}

	protected void handleSelectableBaseEvents(final SelectableBaseViewEvent type, final Object data) {
		switch (type) {
			case SELECT:
				handleSelectEvent((T) data);
				break;
			case UNSELECT:
				handleUnselectEvent();
				break;
		}
	}

	protected void handleSelectEvent(final T item) {
	}

	protected void handleUnselectEvent() {
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
}
