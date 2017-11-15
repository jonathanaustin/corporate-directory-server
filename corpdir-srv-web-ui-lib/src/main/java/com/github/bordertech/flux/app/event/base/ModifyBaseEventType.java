package com.github.bordertech.flux.app.event.base;

import com.github.bordertech.flux.app.event.ModifyEventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ModifyBaseEventType implements ModifyEventType {
	DELETE,
	CREATE,
	UPDATE
}
