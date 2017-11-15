package com.github.bordertech.flux.event.base;

import com.github.bordertech.flux.event.StoreEventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveEventType implements StoreEventType {
	RETRIEVE,
	REFRESH,
	RETRIEVE_ASYNC,
	RETRIEVE_ASYNC_OK,
	RETRIEVE_ASYNC_ERROR,
	REFRESH_ASYNC
}
