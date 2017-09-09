package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.event.SearchEventType;
import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.model.ServiceModel;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import java.util.List;

/**
 * Controller for a Polling View and List View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public class PollingListCtrl<S, T> extends DefaultController implements SearchModelKey {

	@Override
	public void setupController() {
		super.setupController();

		// LIST Listeners
		for (ListEventType type : ListEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleListEvents(event);
				}
			};
			registerListener(listener, type);
		}

		// POLLING Listeners
		for (PollingEventType type : PollingEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handlePollingEvents(event);
				}
			};
			registerListener(listener, type);
		}

		// SEARCH Listeners
		for (SearchEventType type : SearchEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleSearchEvents(event);
				}
			};
			registerListener(listener, type);
		}

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getPollingView() == null) {
			throw new IllegalStateException("A polling view has not been set.");
		}
		if (getListView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
		if (getSearchModelKey() == null) {
			throw new IllegalStateException("A search model key has not been set.");
		}
	}

	/**
	 * Start the polling.
	 *
	 * @param criteria the polling criteria
	 */
	public void doStartPolling(final S criteria) {
		// Setup polling view
		// Wrap search model into ServiceModel for Polling Panel
		final SearchModel<S, List<T>> model = getSearchModelImpl();
		ServiceModel<S, List<T>> wrapper = new ServiceModel<S, List<T>>() {
			@Override
			public List<T> service(final S criteria) {
				return model.search(criteria);
			}
		};
		getPollingView().doSetupAndStartPolling(criteria, wrapper);

		// Reset Listview
		ListView listView = getListView();
		listView.resetView();
		listView.setContentVisible(false);
	}

	public final PollingView<S, List<T>> getPollingView() {
		return getComponentModel().pollingView;
	}

	public final void setPollingView(final PollingView<S, List<T>> pollingView) {
		getOrCreateComponentModel().pollingView = pollingView;
		addView(pollingView);
	}

	public final ListView<T> getListView() {
		return getComponentModel().listView;
	}

	public final void setListView(final ListView<T> listView) {
		getOrCreateComponentModel().listView = listView;
		addView(listView);
	}

	@Override
	public void setSearchModelKey(final String pollingModelKey) {
		getOrCreateComponentModel().searchModelKey = pollingModelKey;
	}

	@Override
	public String getSearchModelKey() {
		return getComponentModel().searchModelKey;
	}

	protected SearchModel<S, List<T>> getSearchModelImpl() {
		return (SearchModel<S, List<T>>) getModel(getSearchModelKey());
	}

	protected void handleListEvents(final Event event) {
		ListEventType type = (ListEventType) event.getQualifier().getEventType();
		switch (type) {
			case START_SEARCH:
				S criteria = (S) event.getData();
				handleStartPollingSearch(criteria);
				break;
			case RESET_LIST:
				handleResetListEvent();
				break;
			case LOAD_LIST:
				List<T> items = (List<T>) event.getData();
				handleLoadList(items);
				break;
			case REFRESH_LIST:
				handleRefreshList();
				break;
			case ADD_ITEM:
				handleAddItemEvent((T) event.getData());
				break;
			case REMOVE_ITEM:
				handleRemoveItemEvent((T) event.getData());
				break;
			case UPDATE_ITEM:
				handleUpdateItemEvent((T) event.getData());
				break;
		}

	}

	protected void handlePollingEvents(final Event event) {

		PollingEventType type = (PollingEventType) event.getQualifier().getEventType();
		switch (type) {
			case STARTED:
				// Do Nothing
				break;
			case ERROR:
				Exception excp = event.getException();
				handlePollingFailedEvent(excp);
				break;
			case COMPLETE:
				List<T> entities = (List<T>) event.getData();
				handlePollingCompleteEvent(entities);
				break;
		}

	}

	protected void handleSearchEvents(final Event event) {
		SearchEventType type = (SearchEventType) event.getQualifier().getEventType();
		switch (type) {
			case SEARCH_VALIDATING:
				dispatchEvent(ListEventType.RESET_LIST);
				break;
			case SEARCH:
				dispatchEvent(ListEventType.START_SEARCH, event.getData());
				break;
		}
	}

	protected void handleResetListEvent() {
		getPollingView().resetView();
		getListView().resetView();
	}

	protected void handleRefreshList() {
		// Do Search Again
		getPollingView().setContentVisible(true);
		getPollingView().doRefreshContent();
		// Reset Listview
		ListView listView = getListView();
		listView.resetView();
		listView.setContentVisible(false);
	}

	protected void handleAddItemEvent(final T item) {
		getListView().addItem(item);
		getListView().showList(true);
	}

	protected void handleRemoveItemEvent(final T item) {
		ListView<T> listView = getListView();
		listView.removeItem(item);
		if (listView.getViewBean().isEmpty()) {
			listView.showList(false);
		}
	}

	protected void handleUpdateItemEvent(final T item) {
		getListView().updateItem(item);
	}

	protected void handlePollingFailedEvent(final Exception excp) {
		getPollingView().setContentVisible(false);
		dispatchMessage(MsgEventType.ERROR, excp.getMessage());
	}

	protected void handlePollingCompleteEvent(final List<T> items) {
		getPollingView().setContentVisible(false);
		handleLoadList(items);
	}

	protected void handleLoadList(final List<T> items) {
		if (items == null || items.isEmpty()) {
			dispatchMessage(MsgEventType.INFO, "No records found");
		} else {
			ListView<T> listView = getListView();
			listView.setViewBean(items);
			listView.setContentVisible(true);
		}
	}

	protected void handleStartPollingSearch(final S criteria) {
		doStartPolling(criteria);
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
	public static class PollingListModel<S, T> extends CtrlModel {

		private PollingView<S, List<T>> pollingView;

		private ListView<T> listView;

		private String searchModelKey;
	}

}
