package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.flux.wc.AbstractStore;
import com.github.bordertech.wcomponents.lib.app.event.NavigationEventType;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;

/**
 * Controller that listens for reset view event and resets its parent combo view.
 *
 * @author jonathan
 */
public class ResetViewCtrl extends AbstractStore {

	@Override
	public void setupController() {
		super.setupController();
		// Reset EVENT
		registerListener(NavigationEventType.RESET_VIEW, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleResetEvent();
			}
		});
	}

	protected void handleResetEvent() {
		ComboView view = findParentCombo();
		if (view != null) {
			view.resetView();
		}
	}

}
