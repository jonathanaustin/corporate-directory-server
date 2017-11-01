package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.flux.wc.DefaultStore;
import com.github.bordertech.wcomponents.lib.app.view.event.NavigationViewEvent;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;

/**
 * Controller that listens for reset view event and resets its parent combo view.
 *
 * @author jonathan
 */
public class ResetViewCtrl extends DefaultStore {

	@Override
	public void setupController() {
		super.setupController();
		// Reset EVENT
		registerListener(NavigationViewEvent.RESET_VIEW, new Listener() {
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
