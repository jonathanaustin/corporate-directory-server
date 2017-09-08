package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * List event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ListEventType implements EventType {
	START_SEARCH,
	LOAD_LIST,
	REFRESH_LIST,
	RESET_LIST,
	ADD_ITEM,
	UPDATE_ITEM,
	REMOVE_ITEM,
	SELECT
}
