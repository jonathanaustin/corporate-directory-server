package com.github.bordertech.flux.wc.app.view.smart.polling;

import com.github.bordertech.flux.app.action.RetrieveActionType;
import com.github.bordertech.flux.app.action.CallType;
import com.github.bordertech.flux.app.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.util.FluxUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.RetrieveOutcomeBaseViewEvent;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;

/**
 * Abstract Polling Smart View that retrieves data from a store.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <R> the result type
 * @param <T> the view type
 */
public abstract class AbstractPollingRetrieveSmartView<S, R, T> extends DefaultPollingMessageSmartView<T> {

	public AbstractPollingRetrieveSmartView(final String viewId, final String template) {
		super(viewId, template);
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof RetrieveOutcomeBaseViewEvent) {
			handleRetrieveOutcomeBaseEvents((RetrieveOutcomeBaseViewEvent) event, data);
		}
	}

	protected void handleRetrieveOutcomeBaseEvents(final RetrieveOutcomeBaseViewEvent type, final Object data) {
		switch (type) {
			case RETRIEVE_OK:
				handleRetrieveOKEvent((R) data);
				break;
			case RETRIEVE_ERROR:
				handleRetrieveErrorEvent((Exception) data);
				break;
		}
	}

	protected abstract void handleRetrieveOKEvent(final R result);

	protected void handleRetrieveErrorEvent(final Exception excp) {
		dispatchMessageError("Error loading details. " + excp.getMessage());
	}

	@Override
	protected void handlePollingStartEvent(final PollingBaseViewEvent type) {
		FluxUtil.dispatchRetrieveAction(getStoreKey(), getStoreRetrieveType(), getStoreCriteria(), CallType.CALL_ASYNC);
	}

	@Override
	protected void handlePollingCheckStatusEvent() {
		boolean done = FluxUtil.isRetrieveStoreActionDone(getStoreKey(), getStoreRetrieveType(), getStoreCriteria());
		if (done) {
			setPollingStatus(PollingStatus.STOPPED);
			handleStoreResult();
		}
	}

	protected void handleStoreResult() {
		resetContent();
		try {
			R result = (R) FluxUtil.getRetrieveStoreActionResult(getStoreKey(), getStoreRetrieveType(), getStoreCriteria());
			dispatchViewEvent(RetrieveOutcomeBaseViewEvent.RETRIEVE_OK, result);
		} catch (Exception e) {
			dispatchViewEvent(RetrieveOutcomeBaseViewEvent.RETRIEVE_ERROR, e);
		}
	}

	public void setStoreRetrieveType(final RetrieveActionType retrieveType) {
		getOrCreateComponentModel().retrieveType = retrieveType;
	}

	public RetrieveActionType getStoreRetrieveType() {
		return getComponentModel().retrieveType;
	}

	public void setStoreKey(final String storeKey) {
		getOrCreateComponentModel().storeKey = storeKey;
	}

	public String getStoreKey() {
		return getComponentModel().storeKey;
	}

	public void setStoreCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	public S getStoreCriteria() {
		return getComponentModel().criteria;
	}

	@Override
	protected PollingStoreModel<S> newComponentModel() {
		return new PollingStoreModel();
	}

	@Override
	protected PollingStoreModel<S> getComponentModel() {
		return (PollingStoreModel) super.getComponentModel();
	}

	@Override
	protected PollingStoreModel<S> getOrCreateComponentModel() {
		return (PollingStoreModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class PollingStoreModel<S> extends SmartViewModel {

		private RetrieveActionType retrieveType = RetrieveBaseActionType.FETCH;

		private String storeKey;

		private S criteria;
	}
}
