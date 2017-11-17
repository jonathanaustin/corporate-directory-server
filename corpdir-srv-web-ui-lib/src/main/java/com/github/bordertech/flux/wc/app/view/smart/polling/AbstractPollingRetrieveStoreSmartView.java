package com.github.bordertech.flux.wc.app.view.smart.polling;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.event.RetrieveActionType;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.flux.app.store.retrieve.RetrieveStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.wc.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;

/**
 * Abstract Polling Smart View that retrieves data from a store.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <R> the result type
 * @param <T> the view type
 */
public abstract class AbstractPollingRetrieveStoreSmartView<S, R, T> extends DefaultPollingMessageSmartView<T> {

	public AbstractPollingRetrieveStoreSmartView(final String viewId, final String template) {
		super(viewId, template);
	}

	@Override
	protected void handlePollingStartEvent(final PollingBaseViewEvent type) {
		dispatchEvent(getStoreRetrieveType(), getStoreCriteria(), RetrieveActionType.CALL_ASYNC);
	}

	@Override
	protected void handlePollingCheckStatusEvent() {
		if (isStoreDone()) {
			setPollingStatus(PollingStatus.STOPPED);
			handleStoreResult();
		}
	}

	protected void handleStoreResult() {
		resetContent();
		try {
			R result = (R) getStore().getEventResult(getStoreRetrieveType(), getStoreCriteria());
			handleResultSuccessful(result);
		} catch (Exception e) {
			handleResultError(e);
		}
	}

	protected abstract void handleResultSuccessful(final R result);

	protected void handleResultError(final Exception excp) {
		dispatchMessageError("Error loading details. " + excp.getMessage());
	}

	public void setStoreRetrieveType(final RetrieveEventType retrieveType) {
		getOrCreateComponentModel().retrieveType = retrieveType;
	}

	public RetrieveEventType getStoreRetrieveType() {
		return getComponentModel().retrieveType;
	}

	public void setStoreType(final StoreType storeType) {
		getOrCreateComponentModel().storeType = storeType;
	}

	public StoreType getStoreType() {
		return getComponentModel().storeType;
	}

	public void setStoreCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	public S getStoreCriteria() {
		return getComponentModel().criteria;
	}

	protected boolean isStoreDone() {
		return getStore().isEventDone(getStoreRetrieveType(), getStoreCriteria());
	}

	protected RetrieveStore getStore() {
		return StoreUtil.getStore(getStoreType(), getFullQualifier());
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
	public static class PollingStoreModel<S> extends ViewContainerModel {

		private RetrieveEventType retrieveType = RetrieveBaseEventType.RETRIEVE;

		private StoreType storeType;

		private S criteria;
	}
}
