package com.github.bordertech.flux.crud.store;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.StoreActionType;
import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.action.retrieve.CallType;
import com.github.bordertech.flux.crud.action.retrieve.RetrieveAction;
import com.github.bordertech.flux.crud.factory.FluxFactory;
import com.github.bordertech.taskmanager.service.AsyncException;

/**
 *
 * @author jonathan
 */
public class StoreUtil {

//    @Inject
//    private static Iterable<Store> allStores;
//    public static List<Store> getAllBooks() {
//        List<Store> retVal = new ArrayList<Store>();
//
//        for (Store store : allStores) {
//            retVal.add(store);
//        }
//        return retVal;
//    }
	private StoreUtil() {
	}

	public static Dispatcher getDispatcher() {
		return FluxFactory.getDispatcher();
	}

	public static <T extends ActionCreator> T getActionCreator(final String key) {
		return (T) getDispatcher().getActionCreator(key);
	}

	public static <T extends Store> T getStore(final String key) {
		return (T) getDispatcher().getStore(key);
	}

	/**
	 * Helper method to dispatch a FETCH action.
	 *
	 * @param storeKey the target store
	 * @param data the action data
	 * @param callType the call type
	 */
	public static void dispatchFetchAction(final String storeKey, final Object data, final CallType callType) {
		dispatchRetrieveAction(storeKey, RetrieveActionBaseType.FETCH, data, callType);
	}

	/**
	 * Helper method to dispatch a SEARCH action.
	 *
	 * @param storeKey the target store
	 * @param data the action data
	 * @param callType the call type
	 */
	public static void dispatchSearchAction(final String storeKey, final Object data, final CallType callType) {
		dispatchRetrieveAction(storeKey, RetrieveActionBaseType.SEARCH, data, callType);
	}

	/**
	 * Helper method to dispatch a retrieve action type.
	 *
	 * @param storeKey the target store
	 * @param actionType the retrieve action type
	 * @param data the action data
	 * @param callType the retrieve call type
	 */
	public static void dispatchRetrieveAction(final String storeKey, final RetrieveActionType actionType, final Object data, final CallType callType) {
		DefaultAction event = new RetrieveAction(actionType, storeKey, data, callType);
		dispatchAction(event);
	}

	/**
	 * Helper method to dispatch a Store action.
	 *
	 * @param storeKey the target store
	 * @param actionType the retrieve action type
	 * @param data the action data
	 */
	public static void dispatchStoreAction(final String storeKey, final StoreActionType actionType, final Object data) {
		DefaultAction event = new DefaultAction(actionType, storeKey, data);
		dispatchAction(event);
	}

	public static void dispatchAction(final Action action) {
		getDispatcher().dispatch(action);
	}

	/**
	 * Helper method to check if a Store FETCH action is done.
	 *
	 * @param storeKey the target store
	 * @param data the action data
	 * @return true if the store action is done
	 * @throws AsyncException exception processing async action
	 */
	public static boolean isFetchActionDone(final String storeKey, final Object data) throws AsyncException {
		return isRetrieveStoreActionDone(storeKey, RetrieveActionBaseType.FETCH, data);
	}

	/**
	 * Helper method to check if a Store SEARCH action is done.
	 *
	 * @param storeKey the target store
	 * @param data the action data
	 * @return true if the store action is done
	 * @throws AsyncException exception processing async action
	 */
	public static boolean isSearchActionDone(final String storeKey, final Object data) throws AsyncException {
		return isRetrieveStoreActionDone(storeKey, RetrieveActionBaseType.SEARCH, data);
	}

	/**
	 * Helper method to check if a Store action is done.
	 *
	 * @param storeKey the target store
	 * @param actionType the retrieve action type
	 * @param data the action data
	 * @return true if the store action is done
	 * @throws AsyncException exception processing async action
	 */
	public static boolean isRetrieveStoreActionDone(final String storeKey, final RetrieveActionType actionType, final Object data) throws AsyncException {
		RetrieveActionStore store = getStore(storeKey);
		return store.isAsyncDone(actionType, data);
	}

	/**
	 * Helper method to get the store FETCH action result.
	 *
	 * @param storeKey the target store
	 * @param data the action data
	 * @return true if the store action is done
	 */
	public static <T> T getFetchActionResult(final String storeKey, final Object data) throws RetrieveActionException {
		return getRetrieveStoreActionResult(storeKey, RetrieveActionBaseType.FETCH, data);
	}

	/**
	 * Helper method to get the store FETCH action result.
	 *
	 * @param storeKey the target store
	 * @param data the action data
	 * @return true if the store action is done
	 */
	public static <T> T getSearchActionResult(final String storeKey, final Object data) throws RetrieveActionException {
		return getRetrieveStoreActionResult(storeKey, RetrieveActionBaseType.SEARCH, data);
	}

	/**
	 * Helper method to get the store retrieve action result.
	 *
	 * @param storeKey the target store
	 * @param actionType the retrieve action type
	 * @param data the action data
	 * @return true if the store action is done
	 */
	public static <T> T getRetrieveStoreActionResult(final String storeKey, final RetrieveActionType actionType, final Object data) throws RetrieveActionException {
		RetrieveActionStore store = getStore(storeKey);
		return (T) store.getActionResult(actionType, data);
	}

}
