package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.event.CriteriaEvent;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.polling.AbstractPollingView;
import com.github.bordertech.wcomponents.lib.polling.PollingEvent;
import com.github.bordertech.wcomponents.lib.polling.PollingException;
import com.github.bordertech.wcomponents.lib.polling.PollingView;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class AbstractCriteriaListCtrl<S, T> extends DefaultView implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final CriteriaView<S> criteriaView;

	private final PollingView<S, List<T>> pollingView;

	private final ListView<T> listView;

	public AbstractCriteriaListCtrl(final CriteriaView<S> criteriaView, final ListView<T> listView) {
		super(criteriaView.getDispatcher());
		this.criteriaView = criteriaView;
		this.listView = listView;

		pollingView = new AbstractPollingView<S, List<T>>(getDispatcher()) {
			@Override
			public List<T> doPollingAction(final S criteria) throws PollingException {
				return doSearchServiceCall(criteria);
			}

			@Override
			protected void handleExceptionResult(final Exception excp) {
				// Do Nothing
			}

			@Override
			protected void handleSuccessfulResult(final List<T> result) {
				// Do Nothing
			}
		};

		WDiv holder = getHolder();

		holder.add(messages);
		holder.add(criteriaView);
		holder.add(pollingView);
		holder.add(listView);

		// Hide view contents
		pollingView.hideHolder();
		listView.hideHolder();

		// AJAX Targets
		criteriaView.addEventTarget(messages);
		criteriaView.addEventTarget(pollingView);
		criteriaView.addEventTarget(listView);

		pollingView.addEventTarget(messages);
		pollingView.addEventTarget(pollingView);
		pollingView.addEventTarget(listView);

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

	public CriteriaView<S> getCriteriaView() {
		return criteriaView;
	}

	public PollingView<S, List<T>> getPollingView() {
		return pollingView;
	}

	public ListView<T> getListView() {
		return listView;
	}

	protected void handleSearchEvent(final S criteria) {
		pollingView.reset();
		listView.reset();
		pollingView.setPollingCriteria(criteria);
		pollingView.showHolder();
	}

	protected void handleSearchFailedEvent(final Exception excp) {
		listView.reset();
		messages.error(excp.getMessage());
	}

	protected void handleSearchCompleteEvent(final List<T> result) {
		listView.reset();
		if (result == null || result.isEmpty()) {
			messages.info("No records found");
		} else {
			listView.setEntities(result);
			listView.showHolder();
		}
	}

	abstract protected List<T> doSearchServiceCall(final S criteria);

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
