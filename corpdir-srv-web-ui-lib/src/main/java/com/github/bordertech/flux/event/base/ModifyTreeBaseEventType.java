package com.github.bordertech.flux.event.base;

import com.github.bordertech.flux.event.ModifyEventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ModifyTreeBaseEventType implements ModifyEventType {
	ADD_CHILD,
	REMOVE_CHILD
}
