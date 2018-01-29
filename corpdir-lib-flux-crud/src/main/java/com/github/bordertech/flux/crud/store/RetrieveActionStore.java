package com.github.bordertech.flux.crud.store;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.crud.action.RetrieveActionType;

/**
 * Store that retrieves its state via the data api.
 *
 * @author jonathan
 */
public interface RetrieveActionStore extends Store {

	boolean isAsyncDone(final RetrieveActionType type, final Object criteria);

	Object getActionResult(final RetrieveActionType type, final Object criteria) throws RetrieveActionException;

	Object getActionResultCacheOnly(final RetrieveActionType type, final Object criteria) throws RetrieveActionException;

}
