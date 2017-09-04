package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
import com.github.bordertech.wcomponents.lib.mvc.impl.MessageCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class ListWithCriteriaView<S, T> extends DefaultComboView implements ListView<T> {

	private final MessageCtrl messageCtrl;

	private final ToolbarView toolbarView;

	private final CriteriaView<S> criteriaView;

	private final ListView<T> listView;

	private final DefaultPollingView<S, List<T>> pollingView;

	public ListWithCriteriaView(final Dispatcher dispatcher, final String qualifier, final CriteriaView<S> criteriaView, final ListView<T> listView) {
		super("wclib/hbs/layout/combo-list-crit.hbs", dispatcher, qualifier);

		// Messages (default to show all)
		messageCtrl = new MessageCtrl(dispatcher, qualifier);
		messageCtrl.setMessageView(new DefaultMessageView(dispatcher, qualifier));

		// Views
		this.toolbarView = new DefaultToolbarView(dispatcher, qualifier);
		this.criteriaView = criteriaView;
		this.listView = listView;
		this.pollingView = new DefaultPollingView<>(dispatcher, qualifier);

		// Ctrl
		ListWithCriteriaCtrl<S, T> ctrl = new ListWithCriteriaCtrl<>(dispatcher, qualifier);
		ctrl.setCriteriaView(criteriaView);
		ctrl.setPollingView(pollingView);
		ctrl.setListView(listView);

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", messageCtrl.getMessageView());
		content.addTaggedComponent("vw-ctrl-msg", messageCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-crit", criteriaView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", listView);

		// Default visibility
		listView.setContentVisible(false);
	}

	public final MessageCtrl getMessageCtrl() {
		return messageCtrl;
	}

	public CriteriaView<S> getCriteriaView() {
		return criteriaView;
	}

	public DefaultPollingView<S, List<T>> getPollingView() {
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

}
