package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.CollectionEventType;
import java.util.Collection;

/**
 * Controller for a Polling View and Collection View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class PollingCollectionCtrl<S, T, C extends Collection<T>> extends AbstractPollingMainCtrl<S, T, C> {

	@Override
	protected void handleRefreshList() {
		super.handleRefreshList();
		dispatchEvent(CollectionEventType.RESET_COLLECTION);
	}

	@Override
	protected void handlePollingCompleteEvent(final C items) {
		super.handlePollingCompleteEvent(items);
		dispatchEvent(CollectionEventType.LOAD_ITEMS, items);
	}

	@Override
	protected void handleStartPollingSearch(S criteria) {
		super.handleStartPollingSearch(criteria);
		dispatchEvent(CollectionEventType.RESET_COLLECTION);
	}

}
