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
	RETRIEVE_OK,
	RETRIEVE_ERROR,
	REFRESH,
	REFRESH_OK,
	REFRESH_ERROR
}
