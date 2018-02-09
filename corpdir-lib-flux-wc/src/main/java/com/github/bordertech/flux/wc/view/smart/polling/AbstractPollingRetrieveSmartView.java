package com.github.bordertech.flux.wc.view.smart.polling;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.store.SearchStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.view.consumer.StoreConsumerByKey;
import com.github.bordertech.flux.wc.view.event.base.PollingBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.RetrieveOutcomeBaseEventType;
import com.github.bordertech.taskmaster.service.CallType;
import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.wcomponents.addons.polling.PollingStatus;
import com.github.bordertech.wcomponents.util.SystemException;

/**
 * Abstract Polling Smart View that retrieves data from a store.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <R> the result type
 * @param <T> the view type
 */
public abstract class AbstractPollingRetrieveSmartView<S, R, T> extends DefaultPollingMessageSmartView<T> implements StoreConsumerByKey<Store> {

	public AbstractPollingRetrieveSmartView(final String viewId, final String template) {
		this(viewId, template, true);
	}

	public AbstractPollingRetrieveSmartView(final String viewId, final String template, final boolean persistent) {
		super(viewId, template, persistent);
	}

	public void setStoreCallType(final CallType callType) {
		getOrCreateComponentModel().callType = callType == null ? CallType.REFRESH_ASYNC : callType;
	}

	public CallType getStoreCallType() {
		return getComponentModel().callType;
	}

	@Override
	public void setStoreKey(final String storeKey) {
		getOrCreateComponentModel().storeKey = storeKey;
	}

	@Override
	public String getStoreKey() {
		return getComponentModel().storeKey;
	}

	@Override
	public Store getStoreByKey() {
		return StoreUtil.getStore(getStoreKey());
	}

	public void setStoreCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	public S getStoreCriteria() {
		return getComponentModel().criteria;
	}

	protected ResultHolder<S, R> doStoreRetrieve() {
		Store store = getStoreByKey();
		if (store instanceof SearchStore) {
			SearchStore searchStore = (SearchStore) store;
			return searchStore.search(getStoreCriteria(), getStoreCallType());
		} else {
			throw new IllegalStateException("Store type is not handled.");
		}
	}

	abstract protected void handleRetrieveOKEvent(final R result);

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

	protected void handleRetrieveErrorEvent(final Exception excp) {
		dispatchMessageError("Error loading details. " + excp.getMessage());
		if (isUseRetryOnError()) {
			doShowRetry();
		}
	}

	@Override
	protected void handlePollingStartEvent(final PollingBaseEventType type) {
		super.handlePollingStartEvent(type);
		// Check if result is already in the cache (dont need to poll)
		ResultHolder<S, R> resultHolder = doStoreRetrieve();
		if (resultHolder != null) {
			handleStoreResult(resultHolder);
			setContineStart(false);
		}
	}

	@Override
	protected void handlePollingCheckStatusEvent() {
		ResultHolder<S, R> result = doStoreRetrieve();
		if (result != null) {
			setPollingStatus(PollingStatus.STOPPED);
			handleStoreResult(result);
		}
	}

	protected void handleStoreResult(final ResultHolder resultHolder) {
		if (resultHolder.isException()) {
			dispatchViewEvent(RetrieveOutcomeBaseEventType.RETRIEVE_ERROR, resultHolder.getException());
		} else {
			dispatchViewEvent(RetrieveOutcomeBaseEventType.RETRIEVE_OK, resultHolder.getResult());
		}
	}

	@Override
	protected void handlePollingTimeoutEvent() {
		super.handlePollingTimeoutEvent();
		dispatchViewEvent(RetrieveOutcomeBaseEventType.RETRIEVE_ERROR, new SystemException("Polling timeout"));
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

		private CallType callType = CallType.REFRESH_ASYNC;

		private String storeKey;

		private S criteria;
	}
}
