package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.store.retrieve.RetrieveListStore;
import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.wc.app.view.ListView;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.smart.polling.DefaultPollingMessageSmartView;
import com.github.bordertech.wcomponents.Request;
import java.util.Collections;
import java.util.List;

/**
 * Polling View and Collection View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
//public class PollingListView<S, T> extends DefaultPollingMessageSmartView<List<T>> implements ListView<T>, RetrieveListModelKey {
public class RetrieveListSmartView<S, T> extends DefaultPollingMessageSmartView<List<T>> implements ListView<T> {

	private final ListView<T> listView;

	public RetrieveListSmartView(final String viewId, final ListView<T> listView) {
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
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		if (isListStoreDone()) {

		}
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof PollingBaseViewEvent) {
			handlePollingEvents((PollingBaseViewEvent) event, data);
		}
	}

	public void setListStoreType(final StoreType retrieveStore) {
		getOrCreateComponentModel().listStoreType = retrieveStore;
	}

	public StoreType getListStoreType() {
		return getComponentModel().listStoreType;
	}

	public void setListCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	public S getListCriteria() {
		return getComponentModel().criteria;
	}

	protected boolean isListStoreDone() {
		return getListStore().isRetrieveDone(getListCriteria());
	}

	protected List<T> getListStoreValue() {
		try {
			return getListStore().retrieve(getListCriteria());
		} catch (Exception e) {
			resetContent();
			handleViewEvent(getViewId(), MessageBaseViewEvent.ERROR, "Error loading list. " + e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}

	protected RetrieveListStore<S, T> getListStore() {
		return StoreUtil.getStore(getListStoreType(), getFullQualifier());
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
	protected PollingListModel<S> newComponentModel() {
		return new PollingListModel();
	}

	@Override
	protected PollingListModel<S> getComponentModel() {
		return (PollingListModel) super.getComponentModel();
	}

	@Override
	protected PollingListModel<S> getOrCreateComponentModel() {
		return (PollingListModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class PollingListModel<S> extends ViewContainerModel {

		private StoreType listStoreType;

		private S criteria;
	}
}
