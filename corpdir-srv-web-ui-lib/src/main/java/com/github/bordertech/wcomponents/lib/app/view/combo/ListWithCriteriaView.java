package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.SearchPollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.bar.DefaultToolbarView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the search type
 * @param <T> the entity type
 */
public class ListWithCriteriaView<S, T> extends PollingListView<S, T> {

	private final ToolbarView toolbarView;

	private final SearchView<S> searchView;

	public ListWithCriteriaView(final SearchView<S> searchView, final ListView<T> listView) {
		super(listView);

		// Views
		this.toolbarView = new DefaultToolbarView();
		this.searchView = searchView;

		// Polling and List Ctrl
		SearchPollingListCtrl ctrl = getPollingCtrl();
		ctrl.addView(searchView);

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-crit", searchView);

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
