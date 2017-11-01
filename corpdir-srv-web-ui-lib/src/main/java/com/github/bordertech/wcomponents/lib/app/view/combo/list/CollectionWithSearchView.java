package com.github.bordertech.wcomponents.lib.app.view.combo.list;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingSearchCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CollectionView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultToolbarView;
import java.util.Collection;

/**
 * Collection View with a Search View.
 *
 * @author jonathan
 * @param <S> the search type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class CollectionWithSearchView<S, T, C extends Collection<T>> extends PollingListView<S, T, C> {

	private final ToolbarView toolbarView;

	private final SearchView<S> searchView;

	public CollectionWithSearchView(final SearchView<S> searchView, final CollectionView<T, C> collectionView) {
		super(collectionView);

		// Views
		this.toolbarView = new DefaultToolbarView();
		this.searchView = searchView;

		// Polling and List Ctrl
		PollingSearchCtrl ctrl = new PollingSearchCtrl();
		ctrl.setPollingView(getPollingView());
		ctrl.addView(searchView);

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-crit", searchView);

		// Default visibility
		collectionView.setContentVisible(false);
		collectionView.addHtmlClass("wc-margin-n-sm");

	}

	public SearchView<S> getSearchView() {
		return searchView;
	}

	public ToolbarView getToolbarView() {
		return toolbarView;
	}

}
