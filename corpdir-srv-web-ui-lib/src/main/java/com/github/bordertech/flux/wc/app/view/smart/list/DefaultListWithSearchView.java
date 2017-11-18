package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.view.ListView;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.ToolbarView;
import com.github.bordertech.flux.wc.app.view.dumb.toolbar.DefaultToolbarView;
import com.github.bordertech.flux.wc.app.view.event.base.SearchBaseViewEvent;

/**
 * Collection View with a Search View.
 *
 * @author jonathan
 * @param <S> the search type
 * @param <T> the item type
 */
public class DefaultListWithSearchView<S, T> extends DefaultListSmartView<S, T> {

	// Toolbar - Defaults to just the Reset Button and Reset Event is handled AUTO
	private final ToolbarView toolbarView = new DefaultToolbarView("vw-toolbar");

	private final SearchView<S> searchView;

	public DefaultListWithSearchView(final String viewId, final SearchView<S> searchView, final ListView<T> listView) {
		super(viewId, listView);

		// Views
		this.searchView = searchView;
		addComponentToTemplate("vw-toolbar", toolbarView);

		// Default visibility
		listView.setContentVisible(false);
		listView.addHtmlClass("wc-margin-n-sm");

	}

	public SearchView<S> getSearchView() {
		return searchView;
	}

	public ToolbarView getToolbarView() {
		return toolbarView;
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof SearchBaseViewEvent) {
			handleSearchBaseEvents((SearchBaseViewEvent) event, data);
		}
	}

	protected void handleSearchBaseEvents(final SearchBaseViewEvent type, final Object data) {
		switch (type) {
			case SEARCH:
				handleSearchEvent((S) data);
				break;
			case SEARCH_VALIDATING:
				handleSearchValidatingEvent();
				break;
		}
	}

	protected void handleSearchEvent(final S criteria) {
		setStoreCriteria(criteria);
		doStartPolling();
	}

	protected void handleSearchValidatingEvent() {
		getListView().reset();
		getPollingView().reset();
	}

}
