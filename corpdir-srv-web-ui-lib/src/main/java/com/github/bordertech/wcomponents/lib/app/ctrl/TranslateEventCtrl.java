package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultEvent;
import com.github.bordertech.wcomponents.lib.mvc.impl.*;

/**
 * Controller to translate an Event Type to another Event Type and/or qualifier.
 *
 * @author jonathan
 */
public class TranslateEventCtrl extends DefaultController {

	public void translate(final EventType fromType, final EventType toType) {
		// Listen to the "FROM" Type
		registerListener(fromType, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				// Dispatch as the new Event Type
				dispatchEvent(toType, event.getData(), event.getException());
			}
		});
	}

	public void translate(final EventType fromType, final EventType toType, final String qualifier) {
		// Listen to the "FROM" Type
		registerListener(fromType, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				// Dispatch as the new Event Type and Qualifier
				dispatchEvent(new DefaultEvent(toType, qualifier, event.getData(), event.getException()));
			}
		});
	}

}
