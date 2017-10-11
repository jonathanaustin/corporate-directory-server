package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.CollectionEventType;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.event.SearchEventType;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.util.Collection;

/**
 * Controller for a Polling View and Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 * @param <C> the Collection type
 */
public class PollingSearchCtrl<S, T, C extends Collection<T>> extends DefaultController {

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

	public final PollingView<S, C> getPollingView() {
		return getComponentModel().pollingView;
	}

	public final void setPollingView(final PollingView<S, C> pollingView) {
		getOrCreateComponentModel().pollingView = pollingView;
		addView(pollingView);
	}

	protected void handleSearchEvents(final Event event) {
		SearchEventType type = (SearchEventType) event.getQualifier().getEventType();
		switch (type) {
			case SEARCH_VALIDATING:
				dispatchEvent(CollectionEventType.RESET_COLLECTION);
				dispatchEvent(PollingEventType.RESET_POLLING);
				break;
			case SEARCH:
				dispatchEvent(PollingEventType.START_POLLING, event.getData());
				break;
		}
	}

	@Override
	protected PollingListModel<S, T, C> newComponentModel() {
		return new PollingListModel();
	}

	@Override
	protected PollingListModel<S, T, C> getComponentModel() {
		return (PollingListModel) super.getComponentModel();
	}

	@Override
	protected PollingListModel<S, T, C> getOrCreateComponentModel() {
		return (PollingListModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class PollingListModel<S, T, C extends Collection<T>> extends CtrlModel {

		private PollingView<S, C> pollingView;
	}

}
