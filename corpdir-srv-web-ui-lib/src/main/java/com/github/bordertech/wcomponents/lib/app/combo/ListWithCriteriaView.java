package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.SearchPollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class ListWithCriteriaView<S, T> extends DefaultMessageComboView<List<T>> {

	private final ToolbarView toolbarView;

	private final CriteriaView<S> criteriaView;

	private final ListView<T> listView;

	private final PollingView<S, List<T>> pollingView;

	public ListWithCriteriaView(final CriteriaView<S> criteriaView, final ListView<T> listView) {
		this(criteriaView, listView, null);
	}

	public ListWithCriteriaView(final CriteriaView<S> criteriaView, final ListView<T> listView, final String qualifier) {
		super("wclib/hbs/layout/combo-list-crit.hbs", qualifier);

		// Views
		this.toolbarView = new DefaultToolbarView(qualifier);
		this.criteriaView = criteriaView;
		this.listView = listView;
		this.pollingView = new DefaultPollingView<>(qualifier);

		// Polling and List Ctrl
		SearchPollingListCtrl<S, T> ctrl = new SearchPollingListCtrl<>(qualifier);
		ctrl.setPollingView(pollingView);
		ctrl.setListView(listView);
		ctrl.setSearchView(criteriaView);
		ctrl.addView(getMessageView());

		ResetViewCtrl resetCtrl = new ResetViewCtrl(qualifier);

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-crit", criteriaView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", listView);

		// Default visibility
		listView.setContentVisible(false);
	}

	public CriteriaView<S> getCriteriaView() {
		return criteriaView;
	}

	public PollingView<S, List<T>> getPollingView() {
		return pollingView;
	}

	public ListView<T> getListView() {
		return listView;
	}

	public void showList(final boolean show) {
		listView.showList(show);
	}

}
