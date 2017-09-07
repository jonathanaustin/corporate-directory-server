package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Entity action events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum FormCtrlEventType implements EventType {
	REFRESH_OK,
	REFRESH_ERROR,
	LOAD_ERROR,
	DELETE_OK,
	DELETE_ERROR,
	CREATE_OK,
	CREATE_ERROR,
	UPDATE_OK,
	UPDATE_ERROR
}
