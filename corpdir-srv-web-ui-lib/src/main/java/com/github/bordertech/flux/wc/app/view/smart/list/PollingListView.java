package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.view.ListView;
import com.github.bordertech.flux.wc.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.smart.polling.DefaultPollingMessageSmartView;
import java.util.List;

/**
 * Polling View and Collection View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
//public class PollingListView<S, T> extends DefaultPollingMessageSmartView<List<T>> implements ListView<T>, RetrieveListModelKey {
public class PollingListView<S, T> extends DefaultPollingMessageSmartView<List<T>> implements ListView<T> {

	private final ListView<T> listView;

	public PollingListView(final String viewId, final ListView<T> listView) {
		super(viewId, "wclib/hbs/layout/combo-list-crit.hbs");

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
		addComponentToTemplate("vw-list", listView);

		// Default visibility
		listView.setContentVisible(false);
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

//	@Override
//	public void setRetrieveListModelKey(final String key) {
//		getOrCreateComponentModel().collectionModelKey = key;
//	}
//
//	@Override
//	public String getRetrieveListModelKey() {
//		return getComponentModel().collectionModelKey;
//	}
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
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof PollingBaseViewEvent) {
			handlePollingEvents((PollingBaseViewEvent) event, data);
		}
	}

//	@Override
//	protected void handleRefreshList() {
//		super.handleRefreshList();
//		dispatchEvent(.RESET_COLLECTION);
//	}
//
//	@Override
//	protected void handlePollingCompleteEvent(final List<T> items) {
//		super.handlePollingCompleteEvent(items);
//		dispatchEvent(CollectionEventType.LOAD_ITEMS, items);
//	}
//
//	@Override
//	protected void handleStartPollingSearch(final S criteria) {
//		super.handleStartPollingSearch(criteria);
//		dispatchEvent(CollectionEventType.RESET_COLLECTION);
//	}
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
