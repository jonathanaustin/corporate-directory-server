package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Entity action events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ActionEventType implements EventType {
	BACK,
	CANCEL,
	EDIT,
	ADD,
	VIEW,
	SELECT,
	LIST_RESET,
	SEARCH,
	SEARCH_START,
	SEARCH_OK,
	SEARCH_ERROR,
	REFRESH,
	REFRESH_OK,
	REFRESH_ERROR,
	LOAD,
	LOAD_OK,
	LOAD_ERROR,
	DELETE,
	DELETE_OK,
	DELETE_ERROR,
	CREATE,
	CREATE_OK,
	CREATE_ERROR,
	UPDATE,
	UPDATE_OK,
	UPDATE_ERROR,
	ENTITY_MODE_CHANGED,
	RESET_VIEW
}
