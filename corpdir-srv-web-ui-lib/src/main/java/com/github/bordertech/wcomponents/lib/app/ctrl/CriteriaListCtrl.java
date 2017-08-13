package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.app.event.CriteriaEvent;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.View;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.polling.PollingEvent;
import com.github.bordertech.wcomponents.lib.polling.PollingView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class CriteriaListCtrl<S, T> extends DefaultController {

	public CriteriaListCtrl(final Dispatcher dispatcher) {
		super(dispatcher);

		// Listeners
		// Search EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				S criteria = (S) event.getData();
				handleSearchEvent(criteria);
			}
		};
		getDispatcher().register(listener, CriteriaEvent.SEARCH);

		// Polling - FAIL
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				Exception excp = (Exception) event.getData();
				handleSearchFailedEvent(excp);
			}
		};
		getDispatcher().register(listener, PollingEvent.ERROR);

		// Polling - COMPLETE
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				List<T> entities = (List<T>) event.getData();
				handleSearchCompleteEvent(entities);
			}
		};
		getDispatcher().register(listener, PollingEvent.COMPLETE);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			configViews();
			setInitialised(true);
		}
	}

	protected void configViews() {

		PollingView pollingView = getPollingView();
		ListView listView = getListView();

		// Hide view contents
		pollingView.makeHolderInvisible();
		listView.makeHolderInvisible();
	}

	@Override
	public List<AjaxTarget> getEventTargets(final View view, final EventType eventType) {
		List<AjaxTarget> targets = new ArrayList<>();
		targets.add(getPollingView());
		targets.add(getListView());
		targets.add(getViewMessages());
		return targets;
	}

	public final CriteriaView<S> getCriteriaView() {
		return getComponentModel().criteriaView;
	}

	public final void setCriteriaView(final CriteriaView<S> criteriaView) {
		getOrCreateComponentModel().criteriaView = criteriaView;
	}

	public final PollingView<S, List<T>> getPollingView() {
		return getComponentModel().pollingView;
	}

	public final void setPollingView(final PollingView<S, List<T>> pollingView) {
		getOrCreateComponentModel().pollingView = pollingView;
	}

	public final ListView<T> getListView() {
		return getComponentModel().listView;
	}

	public final void setListView(final ListView<T> listView) {
		getOrCreateComponentModel().listView = listView;
	}

	protected void handleSearchEvent(final S criteria) {
		PollingView pollingView = getPollingView();
		ListView listView = getListView();
		pollingView.reset();
		listView.reset();
		pollingView.setPollingCriteria(criteria);
		pollingView.makeHolderVisible();
	}

	protected void handleSearchFailedEvent(final Exception excp) {
		ListView listView = getListView();
		listView.reset();
		getViewMessages().error(excp.getMessage());
	}

	protected void handleSearchCompleteEvent(final List<T> result) {
		ListView listView = getListView();
		listView.reset();
		if (result == null || result.isEmpty()) {
			getViewMessages().info("No records found");
		} else {
			listView.setEntities(result);
			listView.makeHolderVisible();
		}
	}

	@Override
	protected CriteriaListModel newComponentModel() {
		return new CriteriaListModel();
	}

	@Override
	protected CriteriaListModel getComponentModel() {
		return (CriteriaListModel) super.getComponentModel();
	}

	@Override
	protected CriteriaListModel getOrCreateComponentModel() {
		return (CriteriaListModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class CriteriaListModel<S, T> extends CtrlModel {

		private CriteriaView<S> criteriaView;

		private PollingView<S, List<T>> pollingView;

		private ListView<T> listView;
	}

}
