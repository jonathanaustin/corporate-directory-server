package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultEvent;
import com.github.bordertech.wcomponents.lib.mvc.impl.*;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEvent;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEventType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller to translate an Event Type to another Event Type and/or qualifier.
 *
 * @author jonathan
 */
public class TranslateEventCtrl extends DefaultController {

	public void translate(final EventType fromType, final EventType toType) {
		// Make sure the types are different
		if (Objects.equals(fromType, toType)) {
			return;
		}
		// Listen to the "FROM" Type
		registerListener(fromType, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				// Dispatch as the new Event Type
				dispatchEvent(toType, event.getData(), event.getException());
			}
		});
	}

	public void translate(final EventType fromType, final EventType toType, final String toQualifier) {
		// Listen to the "FROM" Type
		registerListener(fromType, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				// Make sure at least the toType and Qualifier are different (not keep dispatching same event)
				if (!Objects.equals(fromType, toType) || !Objects.equals(getFullQualifier(), toQualifier)) {
					// Dispatch as the new Event Type and Qualifier
					dispatchEvent(new DefaultEvent(toType, toQualifier, event.getData(), event.getException()));
				}
			}
		});
	}

	public void translateMessage(final String toQualifier, final MessageEventType... fromMsgType) {
		// Make sure the qualifiers are different
		if (Objects.equals(getFullQualifier(), toQualifier)) {
			throw new IllegalArgumentException("The toQualifier [" + toQualifier + "] must be different from the ctrl qualifier [" + getFullQualifier() + "].");
		}
		// Listen to the "FROM" Type
		List<MessageEventType> types = Arrays.asList(fromMsgType.length == 0 ? MessageEventType.values() : fromMsgType);
		for (MessageEventType type : types) {
			registerListener(type, new Listener() {
				@Override
				public void handleEvent(final Event event) {
					// Make sure the Qualifier is different (not keep dispatching same event)
					if (!Objects.equals(getFullQualifier(), toQualifier)) {
						// Dispatch as the new Event Type and Qualifier
						MessageEvent from = (MessageEvent) event;
						MessageEventType fromType = (MessageEventType) from.getQualifier().getEventType();
						dispatchEvent(new MessageEvent(fromType, toQualifier, from.getMessages(), from.isEncode(), from.getDiags()));
					}
				}
			});
		}
	}

}
