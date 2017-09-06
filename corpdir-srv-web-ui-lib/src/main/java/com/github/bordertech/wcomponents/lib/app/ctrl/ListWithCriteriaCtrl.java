package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.model.ServiceModel;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import java.util.List;

/**
 * Controller for a Criteria View and List View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public class ListWithCriteriaCtrl<S, T> extends DefaultController {

	public ListWithCriteriaCtrl() {
		this(null);
	}

	public ListWithCriteriaCtrl(final String qualifier) {
		super(qualifier);
	}

	@Override
	public void setupListeners() {
		super.setupListeners();
		// Listeners
		// Search EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				S criteria = (S) event.getData();
				handleSearchEvent(criteria);
			}
		};
		registerListener(listener, ActionEventType.SEARCH);

		// SEARCH Start
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSearchStartEvent();
			}
		};
		registerListener(listener, ActionEventType.SEARCH_START);

		// Polling - FAIL
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				Exception excp = event.getException();
				handleSearchFailedEvent(excp);
			}
		};
		registerListener(listener, PollingEventType.ERROR);

		// Polling - COMPLETE
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				List<T> entities = (List<T>) event.getData();
				handleSearchCompleteEvent(entities);
			}
		};
		registerListener(listener, PollingEventType.COMPLETE);

		// Toolbar
		// Back
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleBackEvent();
			}
		};
		registerListener(listener, ActionEventType.BACK);
		// Add
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleAddEvent();
			}
		};
		registerListener(listener, ActionEventType.ADD);
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getPollingView() == null) {
			throw new IllegalStateException("A polling view has not been set.");
		}
		if (getCriteriaView() == null) {
			throw new IllegalStateException("A criteria view has not been set.");
		}
		if (getListView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
		if (getServiceModel() == null) {
			throw new IllegalStateException("A search service has not been set.");
		}
	}

	public final CriteriaView<S> getCriteriaView() {
		return getComponentModel().criteriaView;
	}

	public final void setCriteriaView(final CriteriaView<S> criteriaView) {
		getOrCreateComponentModel().criteriaView = criteriaView;
		addView(criteriaView);
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

	public ServiceModel<S, List<T>> getServiceModel() {
		return (ServiceModel<S, List<T>>) getModel(ServiceModel.class);
	}

	protected void handleBackEvent() {
		resetViews();
	}

	protected void handleAddEvent() {
		// Do Nothing
	}

	protected void handleSearchStartEvent() {
		getPollingView().resetView();
		getListView().resetView();
	}

	protected void handleSearchEvent(final S criteria) {
		// Setup polling view
		PollingView pollingView = getPollingView();
		pollingView.resetView();
		pollingView.setPollingCriteria(criteria == null ? "" : criteria);
		pollingView.setServiceModel(getServiceModel());
		pollingView.setContentVisible(true);

		// Reset Listview
		ListView listView = getListView();
		listView.resetView();
		listView.setContentVisible(false);
	}

	protected void handleSearchFailedEvent(final Exception excp) {
		getPollingView().setContentVisible(false);
		dispatchMessage(MsgEventType.ERROR, excp.getMessage());
	}

	protected void handleSearchCompleteEvent(final List<T> result) {
		getPollingView().setContentVisible(false);
		if (result == null || result.isEmpty()) {
			dispatchMessage(MsgEventType.INFO, "No records found");
		} else {
			ListView<T> listView = getListView();
			listView.setViewBean(result);
			listView.setContentVisible(true);
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
