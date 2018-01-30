package com.github.bordertech.flux.wc.view.smart.polling;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.view.event.base.PollingBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.RetrieveOutcomeBaseEventType;
import com.github.bordertech.taskmanager.service.CallType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;
import com.github.bordertech.wcomponents.util.SystemException;

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

	abstract protected void handleRetrieveOKEvent(final R result);

	abstract protected ResultHolder<S, R> handleRetrieveStoreResult();

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
		ResultHolder<S, R> resultHolder = handleRetrieveStoreResult();
		if (resultHolder != null) {
			handleStoreResult(resultHolder);
			setContineStart(false);
		}
	}

	@Override
	protected void handlePollingCheckStatusEvent() {
		try {
			ResultHolder<S, R> result = handleRetrieveStoreResult();
			if (result != null) {
				setPollingStatus(PollingStatus.STOPPED);
				handleStoreResult(result);
			}
		} catch (ServiceException e) {
			setPollingStatus(PollingStatus.STOPPED);
			dispatchViewEvent(RetrieveOutcomeBaseEventType.RETRIEVE_ERROR, e);
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
