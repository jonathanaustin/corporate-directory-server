package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.event.SearchEventType;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.util.List;

/**
 * Controller for a Polling View and Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public class PollingSearchCtrl<S, T> extends DefaultController {

	@Override
	public void setupController() {
		super.setupController();

		// SEARCH Listeners
		for (SearchEventType type : SearchEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleSearchEvents(event);
				}
			};
			registerListener(type, listener);
		}

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getPollingView() == null) {
			throw new IllegalStateException("A polling view has not been set.");
		}
	}

	public final PollingView<S, List<T>> getPollingView() {
		return getComponentModel().pollingView;
	}

	public final void setPollingView(final PollingView<S, List<T>> pollingView) {
		getOrCreateComponentModel().pollingView = pollingView;
		addView(pollingView);
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
	}

}
