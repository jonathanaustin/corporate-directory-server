package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that retrieves its state via the data api.
 *
 * @author jonathan
 */
public interface RetrieveStore extends Store, RetrieveApi {

	ServiceStatus getEventStatus(final RetrieveEventType type, final Object criteria);

	boolean isEventDone(final RetrieveEventType type, final Object criteria);

}
