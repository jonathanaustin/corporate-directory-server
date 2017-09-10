package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.toolbar.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class ListWithCriteriaView<S, T> extends PollingListView<S, T> {

	private final ToolbarView toolbarView;

	private final SearchView<S> criteriaView;

	public ListWithCriteriaView(final SearchView<S> criteriaView, final ListView<T> listView) {
		super(listView);

		// Views
		this.toolbarView = new DefaultToolbarView();
		this.criteriaView = criteriaView;

		// Polling and List Ctrl
		PollingListCtrl ctrl = getPollingCtrl();
		ctrl.addView(criteriaView);

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-crit", criteriaView);

		// Default visibility
		listView.setContentVisible(false);
	}

	public SearchView<S> getCriteriaView() {
		return criteriaView;
	}

}
