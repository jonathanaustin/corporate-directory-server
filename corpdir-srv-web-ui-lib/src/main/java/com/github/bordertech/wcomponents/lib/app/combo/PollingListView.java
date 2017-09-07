package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class PollingListView<S, T> extends DefaultMessageComboView<List<T>> implements ListView<T> {

	private final ListView<T> listView;

	private final PollingView<S, List<T>> pollingView;

	private final PollingListCtrl<S, T> ctrl;

	public PollingListView(final ListView<T> listView) {
		this(listView, null);
	}

	public PollingListView(final ListView<T> listView, final String qualifier) {
		super("wclib/hbs/layout/combo-list-crit.hbs", qualifier);

		// Views
		this.listView = listView;
		this.pollingView = new DefaultPollingView<>(qualifier);

		// Polling and List Ctrl
		ctrl = new PollingListCtrl<>(qualifier);
		ctrl.setPollingView(pollingView);
		ctrl.setListView(listView);
		ctrl.addView(getMessageView());

		ResetViewCtrl resetCtrl = new ResetViewCtrl(qualifier);

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl-pl", ctrl);
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

	public PollingListCtrl<S, T> getPollingCtrl() {
		return ctrl;
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
	public void addItem(final T entity) {
		listView.addItem(entity);
	}

	@Override
	public void removeItem(final T entity) {
		listView.removeItem(entity);
	}

	@Override
	public void updateItem(final T entity) {
		listView.updateItem(entity);
	}

	@Override
	public void showList(final boolean show) {
		listView.showList(show);
	}

}
