package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.app.event.ModifyEventType;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;

/**
 * Modify event creator used by views.
 *
 * @author jonathan
 */
public class DefaultModifyCreator implements ModifyCreator {

	private final String qualifier;

	public DefaultModifyCreator(final String qualifier) {
		this.qualifier = qualifier;
	}

	public String getQualifier() {
		return qualifier;
	}

	protected void dispatchModifyEvent(final ModifyEventType eventType, final Object entity) {
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, getQualifier()), entity);
		getDispatcher().dispatch(event);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

}
