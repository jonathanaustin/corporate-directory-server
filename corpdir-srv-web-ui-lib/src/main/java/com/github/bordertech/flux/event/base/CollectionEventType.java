package com.github.bordertech.flux.event.base;

import com.github.bordertech.flux.event.StoreEventType;

/**
 * Collection event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum CollectionEventType implements StoreEventType {
	LOAD_ITEMS,
	RESET_ITEMS,
	ADD_ITEM,
	UPDATE_ITEM,
	REMOVE_ITEM
}
