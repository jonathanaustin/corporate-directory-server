package com.github.bordertech.flux.crud.store.retrieve;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.crud.action.CallType;
import com.github.bordertech.flux.crud.action.RetrieveAction;
import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.AsyncOutcomeBaseActionType;
import com.github.bordertech.flux.crud.action.base.EntityActionType;
import com.github.bordertech.flux.crud.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract retrieve store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public abstract class AbstractRetrieveStore extends DefaultStore implements RetrieveStore {

	public AbstractRetrieveStore(final String storeKey) {
		super(storeKey);
	}

	@Override
	public ServiceStatus getActionStatus(final RetrieveActionType type, final Object criteria) {
		checkAsyncResult(type, criteria);
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getServiceStatus(key);
	}

	@Override
	public boolean isActionDone(final RetrieveActionType type, final Object criteria) {
		ServiceStatus status = getActionStatus(type, criteria);
		return status == ServiceStatus.COMPLETE || status == ServiceStatus.ERROR;
	}

	@Override
	public Object getActionResult(final RetrieveActionType type, final Object criteria) {

		// Check if have result
		ResultHolder<?, ?> holder = getResultHolder(type, criteria);
		if (holder == null) {
			if (getActionStatus(type, criteria) == ServiceStatus.PROCESSING) {
				throw new IllegalStateException("Item is still being retrieved.");
			}
			// Call SYNC (ie retrieve immediately)
			holder = handleServiceCallAction(type, criteria, false);
		}

		if (holder.isException()) {
			Exception excp = holder.getException();
			if (excp instanceof ServiceException) {
				throw (ServiceException) excp;
			}
			throw new ServiceException(excp.getMessage(), excp);
		}

		return holder.getResult();
	}

	@Override
	public Set<String> registerListeners() {
		Set<String> ids = new HashSet<>();
		// Retrieve Listeners
		for (RetrieveBaseActionType type : RetrieveBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleRetrieveBaseActions((RetrieveAction) action);
				}
			};
			String id = registerListener(type, listener);
			ids.add(id);
		}
		// Async Outcome Listeners
		for (AsyncOutcomeBaseActionType type : AsyncOutcomeBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleAsyncOutcomeBaseActions((RetrieveAction) action);
				}
			};
			String id = registerListener(type, listener);
			ids.add(id);
		}
		// Action Listeners
		for (EntityActionType type : EntityActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleModifyBaseActions(action);
				}
			};
			String id = registerListener(type, listener);
			ids.add(id);
		}
		return ids;
	}

	protected void handleModifyBaseActions(final Action action) {
	}

	protected void handleRetrieveBaseActions(final RetrieveAction action) {
		RetrieveBaseActionType type = (RetrieveBaseActionType) action.getKey().getType();
		CallType callType = action.getCallType();
		boolean changed = false;
		switch (callType) {
			case CALL_SYNC:
				handleServiceCallAction(type, action.getData(), false);
				changed = true;
				break;
			case CALL_ASYNC:
				handleServiceCallAction(type, action.getData(), true);
				break;
			case REFRESH_SYNC:
				handleRefreshAction(type, action.getData(), false);
				changed = true;
				break;
			case REFRESH_ASYNC:
				handleRefreshAction(type, action.getData(), true);
				changed = true;
				break;

			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected ResultHolder<?, ?> handleServiceCallAction(final RetrieveActionType type, final Object criteria, final boolean async) {
		String key = getResultCacheKey(type, criteria);
		ServiceAction action = new ServiceAction() {
			@Override
			public Object service(final Object criteria) {
				return doRetrieveServiceCall(type, criteria);
			}
		};
		if (async) {
			ServiceUtil.handleAsyncServiceCall(key, criteria, action);
			return null;
		} else {
			return ServiceUtil.handleCachedServiceCall(key, criteria, action);
		}
	}

	protected void handleAsyncOutcomeBaseActions(final RetrieveAction action) {
		AsyncOutcomeBaseActionType type = (AsyncOutcomeBaseActionType) action.getKey().getType();
		boolean changed = false;
		switch (type) {
			case ASYNC_ERROR:
				handleAsyncErrorAction(type, (ResultHolder) action.getData());
				changed = true;
				break;
			case ASYNC_OK:
				handleAsyncOKAction(type, (ResultHolder) action.getData());
				changed = true;
				break;
			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected void handleAsyncOKAction(final RetrieveActionType type, final ResultHolder<?, ?> holder) {
		// OK
	}

	protected void handleAsyncErrorAction(final RetrieveActionType type, final ResultHolder<?, ?> holder) {
		// ERROR
	}

	protected void handleRefreshAction(final RetrieveActionType type, final Object criteria, final boolean async) {
		clearResultHolder(type, criteria);
		handleServiceCallAction(type, criteria, async);
	}

	protected String getResultCacheKey(final RetrieveActionType type, final Object criteria) {
		String typeDesc = type.toString();
		String suffix = criteria == null ? "" : criteria.toString();
		return getKey() + "-" + typeDesc + "-" + suffix;
	}

	protected ResultHolder<?, ?> getResultHolder(final RetrieveActionType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getResultHolder(key);
	}

	protected void clearResultHolder(final RetrieveActionType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		ServiceUtil.clearResult(key);
	}

	protected void checkAsyncResult(final RetrieveActionType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		// Check if async result available
		ResultHolder resultHolder = ServiceUtil.checkASyncResult(key);
		if (resultHolder != null) {
			if (resultHolder.isException()) {
				dispatchResultAction(AsyncOutcomeBaseActionType.ASYNC_ERROR, null, resultHolder);
			} else {
				dispatchResultAction(AsyncOutcomeBaseActionType.ASYNC_OK, null, resultHolder);
			}
		}
	}

	/**
	 * Helper method to dispatch an action for this view with the view qualifier automatically added.
	 *
	 * @param actionType the action type
	 * @param callType the retrieve action
	 * @param result the action data
	 */
	protected void dispatchResultAction(final RetrieveActionType actionType, final CallType callType, final ResultHolder<?, ?> result) {
		String qualifier = getKey();
		DefaultAction action = new RetrieveAction(actionType, qualifier, result, callType);
		getDispatcher().dispatch(action);
	}

	/**
	 *
	 * @param type the action type
	 * @param criteria the criteria
	 * @return the result
	 */
	protected abstract Object doRetrieveServiceCall(final RetrieveActionType type, final Object criteria);

}
