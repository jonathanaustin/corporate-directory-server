package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.key.EventType;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum DispatcherEventType implements EventType {
	REGISTER_LISTENER,
	UNREGISTER_LISTENER,
	REGISTER_STORE,
	UNREGISTER_STORE
}
