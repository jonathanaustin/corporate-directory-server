package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.app.event.ModifyEventType;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.flux.key.CreatorKey;
import com.github.bordertech.flux.key.EventKey;

/**
 * Modify event creator used by views.
 *
 * @author jonathan
 */
public class AbstractModifyCreator implements ModifyCreator {

	private final CreatorKey key;

	public AbstractModifyCreator(final CreatorKey key) {
		this.key = key;
	}

	@Override
	public CreatorKey getKey() {
		return key;
	}

	public String getQualifier() {
		return getKey().getQualifier();
	}

	protected void dispatchModifyEvent(final ModifyEventType eventType, final Object entity) {
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, getQualifier()), entity);
		getDispatcher().dispatch(event);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

}
