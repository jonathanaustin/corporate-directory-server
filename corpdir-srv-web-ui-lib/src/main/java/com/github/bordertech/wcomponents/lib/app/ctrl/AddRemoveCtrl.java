package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.*;

/**
 * Controller that listens for reset view event and resets its parent combo view.
 *
 * @author jonathan
 */
public class AddRemoveCtrl extends DefaultController {

	public AddRemoveCtrl() {
		this(null);
	}

	public AddRemoveCtrl(final String qualifier) {
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
				handleSelectEvent(event);
			}
		};
		registerListener(listener, ListEventType.SELECT);
	}

	protected void handleSelectEvent(final Event event) {
		// Translate from SELECT to ADD
		dispatchCtrlEvent(ListEventType.ADD_ITEM, event.getData());
	}

}
