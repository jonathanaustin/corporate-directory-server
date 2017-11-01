package com.github.bordertech.wcomponents.lib.app.store.event;

import com.github.bordertech.flux.EventType;

/**
 * Collection event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum CollectionEventType implements EventType {
	LOAD_ITEMS,
	RESET_COLLECTION,
	ADD_ITEM,
	UPDATE_ITEM,
	REMOVE_ITEM
}
