package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ModelEventType implements EventType {
	CANCEL,
	EDIT,
	RETRIEVE,
	ADD,
	CREATE,
	UPDATE,
	DELETE,
	REFRESH,
	SELECTED
}
