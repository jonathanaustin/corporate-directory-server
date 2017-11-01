package com.github.bordertech.wcomponents.lib.app.view.combo.list;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.wcomponents.lib.app.model.ModelUtil;
import com.github.bordertech.wcomponents.lib.app.model.RetrieveListModel;
import com.github.bordertech.wcomponents.lib.app.model.keys.RetrieveListModelKey;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.event.PollingViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.polling.ServiceAction;
import java.util.List;

/**
 * Polling View and Collection View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class PollingListView<S, T> extends DefaultMessageSmartView<List<T>> implements ListView<T>, RetrieveListModelKey {

	private final ListView<T> listView;

	private final PollingView<S, List<T>> pollingView = new DefaultPollingView<>();

	public PollingListView(final ListView<T> listView) {
		super("wclib/hbs/layout/combo-list-crit.hbs");

		// Views
		this.listView = listView;

//		// Polling and List Ctrl
//		ctrl = new PollingCollectionCtrl<>();
//		ctrl.setPollingView(pollingView);
//		ctrl.addView(getMessageView());
//		CollectionMainCtrl listCtrl = new CollectionMainCtrl();
//		listCtrl.setCollectionView(collectionView);
//		ResetViewCtrl resetCtrl = new ResetViewCtrl();
		// Add views to holder
		addComponentToContentTemplate("vw-poll", pollingView);
		addComponentToContentTemplate("vw-list", listView);

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
	public void setRetrieveListModelKey(final String key) {
		getOrCreateComponentModel().collectionModelKey = key;
	}

	@Override
	public String getRetrieveListModelKey() {
		return getComponentModel().collectionModelKey;
	}

	@Override
	public void setItems(final List<T> items) {
		listView.setItems(items);
	}

	@Override
	public List<T> getItems() {
		return listView.getItems();
	}

	@Override
	public void refreshItems(final List<T> items) {
		listView.refreshItems(items);
	}

	@Override
	public void handleViewEvent(final ViewEventType event, final Object data) {
		super.handleViewEvent(event, data);
		if (event instanceof PollingViewEvent) {
			handlePollingEvents((PollingViewEvent) event, data);
		}
	}

	protected void handlePollingEvents(final PollingViewEvent type, final Object data) {

		switch (type) {
			case START_POLLING:
				S criteria = (S) data;
				handleStartPollingSearch(criteria);
				break;
			case REFRESH:
				handleRefreshList();
				break;
			case STARTED:
				// Do Nothing
				break;
			case ERROR:
				Exception excp = (Exception) data;
				handlePollingFailedEvent(excp);
				break;
			case COMPLETE:
				List<T> entities = (List<T>) data;
				handlePollingCompleteEvent(entities);
				break;
			case RESET_POLLING:
				handleResetPollingEvent();
				break;
		}

	}

	protected void handleRefreshList() {
		// Do Service Again
		getPollingView().setContentVisible(true);
		getPollingView().doRefreshContent();
	}

	protected void handlePollingFailedEvent(final Exception excp) {
		getPollingView().setContentVisible(false);
		handleMessageError(excp.getMessage());
	}

	protected void handlePollingCompleteEvent(final List<T> items) {
		getPollingView().setContentVisible(false);
	}

	protected void handleStartPollingSearch(final S criteria) {
		// Setup polling view
		// Wrap search model into ServiceModel for Polling Panel
		final RetrieveListModel<S, T> model = ModelUtil.getRetrieveListImpl(this);
		ServiceAction<S, List<T>> wrapper = new ServiceAction<S, List<T>>() {
			@Override
			public List<T> service(final S criteria) {
				return model.retrieveCollection(criteria);
			}
		};
		getPollingView().setContentVisible(true);
		getPollingView().doSetupAndStartPolling(criteria, wrapper);
	}

	protected void handleResetPollingEvent() {
		getPollingView().resetView();
	}

	@Override
	protected PollingListModel newComponentModel() {
		return new PollingListModel();
	}

	@Override
	protected PollingListModel getComponentModel() {
		return (PollingListModel) super.getComponentModel();
	}

	@Override
	protected PollingListModel getOrCreateComponentModel() {
		return (PollingListModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class PollingListModel extends ViewContainerModel {

		private String collectionModelKey;
	}
}
