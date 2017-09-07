package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class ListWithCriteriaView<S, T> extends PollingListView<S, T> {

	private final ToolbarView toolbarView;

	private final CriteriaView<S> criteriaView;

	public ListWithCriteriaView(final CriteriaView<S> criteriaView, final ListView<T> listView) {
		this(criteriaView, listView, null);
	}

	public ListWithCriteriaView(final CriteriaView<S> criteriaView, final ListView<T> listView, final String qualifier) {
		super(listView, qualifier);

		// Views
		this.toolbarView = new DefaultToolbarView(qualifier);
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

	public CriteriaView<S> getCriteriaView() {
		return criteriaView;
	}

}
