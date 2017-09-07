package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
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

	public ResetViewCtrl() {
		this(null);
	}

	public ResetViewCtrl(final String qualifier) {
		super(qualifier);
	}

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
		registerListener(listener, ToolbarEventType.RESET_VIEW);
	}

	protected void handleResetEvent() {
		ComboView view = findParentCombo();
		if (view != null) {
			view.resetView();
		}
	}

}
