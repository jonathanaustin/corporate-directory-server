package com.github.bordertech.flux.app.event;

import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.dispatcher.DefaultEvent;

/**
 *
 * @author jonathan
 */
public class RetrieveEvent extends DefaultEvent {

	private final RetrieveActionType actionType;

	public RetrieveEvent(final RetrieveEventType eventType, final String qualifier, final Object data, final RetrieveActionType actionType) {
		super(new EventKey(eventType, qualifier), data);
		this.actionType = actionType;
	}

	public RetrieveActionType getActionType() {
		return actionType;
	}
}
