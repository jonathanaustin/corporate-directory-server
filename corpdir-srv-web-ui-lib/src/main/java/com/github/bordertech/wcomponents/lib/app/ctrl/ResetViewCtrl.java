package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.*;

/**
 * Controller that listens for reset view event and resets its parent combo view.
 *
 * @author jonathan
 */
public class ResetViewCtrl extends DefaultController {

	@Override
	public void setupListeners() {
		super.setupListeners();
		// Default Listeners
		// Reset EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleResetEvent();
			}
		};
		registerListener(listener, ActionEventType.RESET_VIEW);
	}

	protected void handleResetEvent() {
		ComboView view = findParentCombo();
		if (view != null) {
			view.resetView();
		}
	}

}
