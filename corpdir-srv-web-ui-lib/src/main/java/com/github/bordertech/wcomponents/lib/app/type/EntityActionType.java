package com.github.bordertech.wcomponents.lib.app.type;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Entity action events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum EntityActionType implements EventType {
	BACK,
	CANCEL,
	EDIT,
	ADD,
	REFRESH,
	DELETE,
	SAVE
}
