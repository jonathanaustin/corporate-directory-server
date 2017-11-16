package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.event.base.RetrieveActionType;
import com.github.bordertech.flux.app.event.RetrieveEvent;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import com.github.bordertech.taskmanager.service.ServiceUtil;

/**
 * Abstract retrieve store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public abstract class AbstractRetrieveStore extends DefaultStore implements RetrieveStore {

	public AbstractRetrieveStore(final StoreType storeType) {
		this(storeType, null);
	}

	public AbstractRetrieveStore(final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
	}

	@Override
	public ServiceStatus getEventStatus(final RetrieveEventType type, final Object criteria) {
		return getResultStatus(type, criteria);
	}

	@Override
	public boolean isEventDone(final RetrieveEventType type, final Object criteria) {
		return isResultDone(type, criteria);
	}

	protected void handleCallAction(final RetrieveEventType type, final Object criteria, final boolean async) {
		handleServiceCall(type, criteria, async);
	}

	protected void handleAsyncOKAction(final RetrieveEventType type, final ResultHolder<?, ?> holder) {
		// OK
	}

	protected void handleAsyncErrorAction(final RetrieveEventType type, final ResultHolder<?, ?> holder) {
		// ERROR
	}

	protected void handleRefreshAction(final RetrieveEventType type, final Object criteria, final boolean async) {
		clearResultHolder(type, criteria);
		handleCallAction(type, criteria, async);
	}

	protected String getResultCacheKey(final RetrieveEventType type, final Object criteria) {
		String typeDesc = type.toString();
		String suffix = criteria == null ? "" : criteria.toString();
		return getStoreKey().toString() + "-" + typeDesc + "-" + suffix;
	}

	protected ResultHolder<?, ?> getResultHolder(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getResultHolder(key);
	}

	protected void setResultHolder(final RetrieveEventType type, final ResultHolder<?, ?> resultHolder) {
		String key = getResultCacheKey(type, resultHolder.getCriteria());
		ServiceUtil.setResultHolder(key, resultHolder);
	}

	protected void clearResultHolder(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		ServiceUtil.clearResult(key);
	}

	protected Object getResultValue(final RetrieveEventType type, final Object criteria) throws ServiceException {

		// Check if have result
		ResultHolder<?, ?> holder = getResultHolder(type, criteria);
		if (holder == null) {
			throw new ServiceException("No value for this criteria.");
		}

		if (holder.hasException()) {
			Exception excp = holder.getException();
			if (excp instanceof ServiceException) {
				throw (ServiceException) excp;
			}
			throw new ServiceException(excp.getMessage(), excp);
		}

		return holder.getResult();
	}

	protected ServiceStatus getResultStatus(final RetrieveEventType type, final Object criteria) {
		checkAsyncResult(type, criteria);
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getServiceStatus(key);
	}

	protected boolean isResultDone(final RetrieveEventType type, final Object criteria) {
		ServiceStatus status = getResultStatus(type, criteria);
		return status == ServiceStatus.COMPLETE || status == ServiceStatus.ERROR;
	}

	protected void checkAsyncResult(final RetrieveEventType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		// Check if async result available
		ResultHolder resultHolder = ServiceUtil.checkASyncResult(key);
		if (resultHolder != null) {
			RetrieveActionType action = resultHolder.hasException() ? RetrieveActionType.ASYNC_ERROR : RetrieveActionType.ASYNC_OK;
			dispatchResultEvent(type, action, resultHolder);
		}
	}

	protected void handleServiceCall(final RetrieveEventType type, final Object criteria, final boolean async) {
		String key = getResultCacheKey(type, criteria);
		ServiceAction action = getWrappedApiServiceAction(type);
		if (async) {
			ServiceUtil.handleAsyncServiceCall(key, criteria, action);
		} else {
			ServiceUtil.handleServiceCall(key, criteria, action);
		}
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param result the event data
	 */
	protected void dispatchResultEvent(final RetrieveEventType eventType, final RetrieveActionType action, final ResultHolder<?, ?> result) {
		String qualifier = getStoreKey().getQualifier();
		DefaultEvent event = new RetrieveEvent(eventType, qualifier, result, action);
		getDispatcher().dispatch(event);
	}

	/**
	 *
	 * @param type the event type
	 * @return the wrapped API action for the event type.
	 */
	protected abstract ServiceAction<?, ?> getWrappedApiServiceAction(final RetrieveEventType type);

}
