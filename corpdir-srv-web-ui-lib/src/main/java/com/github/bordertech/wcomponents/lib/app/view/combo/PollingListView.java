package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class PollingListView<S, T> extends DefaultMessageComboView<List<T>> implements ListView<T>, SearchModelKey {

	private final ListView<T> listView;

	private final PollingView<S, List<T>> pollingView;

	private final PollingListCtrl<S, T> ctrl;

	public PollingListView(final ListView<T> listView) {
		super("wclib/hbs/layout/combo-list-crit.hbs");

		// Views
		this.listView = listView;
		this.pollingView = new DefaultPollingView<>();

		// Polling and List Ctrl
		ctrl = new PollingListCtrl<>();
		ctrl.setPollingView(pollingView);
		ctrl.addView(getMessageView());

		ListMainCtrl listCtrl = new ListMainCtrl();
		listCtrl.setListView(listView);

		ResetViewCtrl resetCtrl = new ResetViewCtrl();

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl-pol", ctrl);
		content.addTaggedComponent("vw-ctrl-lst", listCtrl);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", listView);

		// Default visibility
		listView.setContentVisible(false);
	}

	public PollingView<S, List<T>> getPollingView() {
		return pollingView;
	}

	public ListView<T> getListView() {
		return listView;
	}

	@Override
	public List<T> getViewBean() {
		return listView.getViewBean();
	}

	@Override
	public void setViewBean(final List<T> viewBean) {
		listView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		listView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return listView.validateView();
	}

	@Override
	public void addItem(final T item) {
		listView.addItem(item);
	}

	@Override
	public void removeItem(final T item) {
		listView.removeItem(item);
	}

	@Override
	public void updateItem(final T item) {
		listView.updateItem(item);
	}

	@Override
	public void showList(final boolean show) {
		listView.showList(show);
	}

	@Override
	public int getSize() {
		return listView.getSize();
	}

	@Override
	public void setSearchModelKey(final String key) {
		ctrl.setSearchModelKey(key);
	}

	@Override
	public String getSearchModelKey() {
		return ctrl.getSearchModelKey();
	}

	public void doStartPolling(final S criteria) {
		ctrl.doStartPolling(criteria);
	}

	@Override
	public int getIndexOfItem(final T item) {
		return getListView().getIndexOfItem(item);
	}

	@Override
	public T getItem(final int idx) {
		return getListView().getItem(idx);
	}

}
