package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.event.SearchEventType;
import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.model.ServiceModel;
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
		dispatchEvent(ListEventType.RESET_LIST);
	}

	public final PollingView<S, List<T>> getPollingView() {
		return getComponentModel().pollingView;
	}

	public final void setPollingView(final PollingView<S, List<T>> pollingView) {
		getOrCreateComponentModel().pollingView = pollingView;
		addView(pollingView);
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

	protected void handlePollingEvents(final Event event) {

		PollingEventType type = (PollingEventType) event.getQualifier().getEventType();
		switch (type) {
			case START_POLLING:
				S criteria = (S) event.getData();
				handleStartPollingSearch(criteria);
				break;
			case REFRESH:
				handleRefreshList();
				break;
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
			case RESET_POLLING:
				handleResetPollingEvent();
				break;
		}

	}

	protected void handleSearchEvents(final Event event) {
		SearchEventType type = (SearchEventType) event.getQualifier().getEventType();
		switch (type) {
			case SEARCH_VALIDATING:
				dispatchEvent(ListEventType.RESET_LIST);
				dispatchEvent(PollingEventType.RESET_POLLING);
				break;
			case SEARCH:
				dispatchEvent(PollingEventType.START_POLLING, event.getData());
				break;
		}
	}

	protected void handleRefreshList() {
		// Do Search Again
		getPollingView().setContentVisible(true);
		getPollingView().doRefreshContent();
		dispatchEvent(ListEventType.RESET_LIST);
	}

	protected void handlePollingFailedEvent(final Exception excp) {
		getPollingView().setContentVisible(false);
		dispatchMessage(MsgEventType.ERROR, excp.getMessage());
	}

	protected void handlePollingCompleteEvent(final List<T> items) {
		getPollingView().setContentVisible(false);
		dispatchEvent(ListEventType.LOAD_LIST, items);
	}

	protected void handleStartPollingSearch(final S criteria) {
		doStartPolling(criteria);
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
	public static class PollingListModel<S, T> extends CtrlModel {

		private PollingView<S, List<T>> pollingView;

		private String searchModelKey;
	}

}
