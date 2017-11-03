package com.github.bordertech.wcomponents.lib.app.view.smart.polling;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;

/**
 * Polling View and Collection View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class DefaultPollingSmartView<S, T> extends DefaultSmartView<T> {

	private final PollingView<S, T> pollingView = new DefaultPollingView<>("vw-poll");

	public DefaultPollingSmartView(final String viewId, final String templateName) {
		super(viewId, templateName);
		addViewToTemplate(pollingView);
	}

	public PollingView<S, T> getPollingView() {
		return pollingView;
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof PollingBaseViewEvent) {
			handlePollingEvents((PollingBaseViewEvent) event, data);
		}
	}

	protected void handlePollingEvents(final PollingBaseViewEvent type, final Object data) {

		switch (type) {
			case START_POLLING:
//				S criteria = (S) data;
//				handleStartPollingSearch(criteria);
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
				T entities = (T) data;
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

	protected void handlePollingCompleteEvent(final T items) {
		getPollingView().setContentVisible(false);
	}

	protected void handleResetPollingEvent() {
		getPollingView().resetView();
	}

}
