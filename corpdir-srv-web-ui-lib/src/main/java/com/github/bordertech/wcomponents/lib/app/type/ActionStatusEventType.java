package com.github.bordertech.wcomponents.lib.app.type;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Entity action events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ActionStatusEventType implements EventType {
	REFRESH_OK,
	LOADED_OK,
	DELETE_OK,
	SAVE_OK,
	ADD_OK,
	LOADED_ERROR,
	REFRESH_ERROR,
	DELETE_ERROR,
	SAVE_ERROR,
	ADD_ERROR
}
