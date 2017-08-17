package com.github.bordertech.wcomponents.lib.app.type;

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
	SEARCH,
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
	SAVE,
	SAVE_OK,
	SAVE_ERROR
}
