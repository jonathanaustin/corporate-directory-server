package com.github.bordertech.flux.event.base;

import com.github.bordertech.flux.event.StoreEventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ModifyEventType implements StoreEventType {
	DELETE,
	DELETE_OK,
	DELETE_ERROR,
	CREATE,
	CREATE_OK,
	CREATE_ERROR,
	UPDATE,
	UPDATE_OK,
	UPDATE_ERROR
}
