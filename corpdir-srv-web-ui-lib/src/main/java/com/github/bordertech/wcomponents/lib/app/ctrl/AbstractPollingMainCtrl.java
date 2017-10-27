package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.model.keys.RetrieveCollectionModelKey;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEventType;
import java.util.Collection;
import com.github.bordertech.wcomponents.lib.app.model.RetrieveCollectionModel;
import com.github.bordertech.wcomponents.polling.ServiceAction;

/**
 * Controller for a Polling View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class AbstractPollingMainCtrl<S, T, C extends Collection<T>> extends DefaultController implements RetrieveCollectionModelKey {

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
			registerListener(type, listener);
		}

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getPollingView() == null) {
			throw new IllegalStateException("A polling view has not been set.");
		}
		if (getRetrieveCollectionModelKey() == null) {
			throw new IllegalStateException("A retrieve collection model key has not been set.");
		}
	}

	public final PollingView<S, C> getPollingView() {
		return getComponentModel().pollingView;
	}

	public final void setPollingView(final PollingView<S, C> pollingView) {
		getOrCreateComponentModel().pollingView = pollingView;
		addView(pollingView);
	}

	@Override
	public void setRetrieveCollectionModelKey(final String collectionModelKey) {
		getOrCreateComponentModel().collectionModelKey = collectionModelKey;
	}

	@Override
	public String getRetrieveCollectionModelKey() {
		return getComponentModel().collectionModelKey;
	}

	protected RetrieveCollectionModel<S, T, C> getRetrieveCollectionImpl() {
		return (RetrieveCollectionModel<S, T, C>) getModel(getRetrieveCollectionModelKey());
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
				C entities = (C) event.getData();
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
		dispatchMessage(MessageEventType.ERROR, excp.getMessage());
	}

	protected void handlePollingCompleteEvent(final C items) {
		getPollingView().setContentVisible(false);
	}

	protected void handleStartPollingSearch(final S criteria) {
		// Setup polling view
		// Wrap search model into ServiceModel for Polling Panel
		final RetrieveCollectionModel<S, T, C> model = getRetrieveCollectionImpl();
		ServiceAction<S, C> wrapper = new ServiceAction<S, C>() {
			@Override
			public C service(final S criteria) {
				return model.retrieveCollection(criteria);
			}
		};
		getPollingView().setContentVisible(true);
		getPollingView().doSetupAndStartPolling(criteria, wrapper);
	}

	protected void handleResetPollingEvent() {
		getPollingView().resetView();
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

		private String collectionModelKey;
	}

}
