package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.OptionsEventType;
import java.util.List;

/**
 * Controller for a Polling View and Input Options View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class PollingInputOptionsCtrl<S, T> extends AbstractPollingMainCtrl<S, T, List<T>> {

	@Override
	protected void handleRefreshList() {
		super.handleRefreshList();
		dispatchEvent(OptionsEventType.RESET_OPTIONS);
	}

	@Override
	protected void handlePollingCompleteEvent(final List<T> items) {
		super.handlePollingCompleteEvent(items);
		dispatchEvent(OptionsEventType.LOAD_OPTIONS, items);
	}

	@Override
	protected void handleStartPollingSearch(S criteria) {
		super.handleStartPollingSearch(criteria);
		dispatchEvent(OptionsEventType.RESET_OPTIONS);
	}

}
