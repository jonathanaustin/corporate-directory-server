package com.github.bordertech.wcomponents.lib.app.view.smart.list;

import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultToolbarView;

/**
 * Collection View with a Search View.
 *
 * @author jonathan
 * @param <S> the search type
 * @param <T> the item type
 */
public class ListWithSearchView<S, T> extends PollingListView<S, T> {

	private final ToolbarView toolbarView = new DefaultToolbarView("vw-toolbar");

	private final SearchView<S> searchView;

	public ListWithSearchView(final String viewId, final SearchView<S> searchView, final ListView<T> listView) {
		super(viewId, listView);

		// Views
		this.searchView = searchView;

//		// Polling and List Ctrl
//		PollingSearchCtrl ctrl = new PollingSearchCtrl();
//		ctrl.setPollingView(getPollingView());
//		ctrl.addView(searchView);
		// Add views to holder
		addViewToTemplate(toolbarView);

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

}
