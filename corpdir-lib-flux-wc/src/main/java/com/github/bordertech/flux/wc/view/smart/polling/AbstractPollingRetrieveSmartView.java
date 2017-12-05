package com.github.bordertech.flux.wc.view.smart.polling;

import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.action.retrieve.CallType;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.view.event.base.RetrieveOutcomeBaseEventType;
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
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof RetrieveOutcomeBaseEventType) {
			handleRetrieveOutcomeBaseEvents((RetrieveOutcomeBaseEventType) event, data);
		}
	}

	protected void handleRetrieveOutcomeBaseEvents(final RetrieveOutcomeBaseEventType type, final Object data) {
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
	protected void handlePollingStartedEvent() {
		StoreUtil.dispatchRetrieveAction(getStoreKey(), getStoreRetrieveType(), getStoreCriteria(), getStoreCallType());
	}

	@Override
	protected void handlePollingCheckStatusEvent() {
		boolean done = StoreUtil.isRetrieveStoreActionDone(getStoreKey(), getStoreRetrieveType(), getStoreCriteria());
		if (done) {
			setPollingStatus(PollingStatus.STOPPED);
			handleStoreResult();
		}
	}

	protected void handleStoreResult() {
		try {
			R result = (R) StoreUtil.getRetrieveStoreActionResult(getStoreKey(), getStoreRetrieveType(), getStoreCriteria());
			dispatchViewEvent(RetrieveOutcomeBaseEventType.RETRIEVE_OK, result);
		} catch (Exception e) {
			dispatchViewEvent(RetrieveOutcomeBaseEventType.RETRIEVE_ERROR, e);
		}
	}

	public void setStoreRetrieveType(final RetrieveActionType retrieveType) {
		getOrCreateComponentModel().retrieveType = retrieveType == null ? RetrieveActionBaseType.FETCH : retrieveType;
	}

	public RetrieveActionType getStoreRetrieveType() {
		return getComponentModel().retrieveType;
	}

	public void setStoreCallType(final CallType callType) {
		getOrCreateComponentModel().callType = callType == null ? CallType.REFRESH_ASYNC : callType;
	}

	public CallType getStoreCallType() {
		return getComponentModel().callType;
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

		private RetrieveActionType retrieveType = RetrieveActionBaseType.SEARCH;

		private CallType callType = CallType.CALL_ASYNC;

		private String storeKey;

		private S criteria;
	}
}
