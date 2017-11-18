package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.app.action.RetrieveActionType;
import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that retrieves its state via the data api.
 *
 * @author jonathan
 */
public interface RetrieveStore extends Store {

	ServiceStatus getActionStatus(final RetrieveActionType type, final Object criteria);

	boolean isActionDone(final RetrieveActionType type, final Object criteria);

	Object getActionResult(final RetrieveActionType type, final Object criteria);

}
