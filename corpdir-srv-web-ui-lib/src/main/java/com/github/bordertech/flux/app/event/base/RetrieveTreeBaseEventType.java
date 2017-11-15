package com.github.bordertech.flux.app.event.base;

import com.github.bordertech.flux.app.event.RetrieveEventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveTreeBaseEventType implements RetrieveEventType {
	RETRIEVE_CHILDREN,
	RETRIEVE_CHILDREN_ASYNC,
	RETRIEVE_CHILDREN_ASYNC_OK,
	RETRIEVE_CHILDREN_ASYNC_ERROR,
	REFRESH_CHILDREN,
	REFRESH_CHILDREN_ASYNC,
	RETRIEVE_ROOT,
	RETRIEVE_ROOT_ASYNC,
	RETRIEVE_ROOT_ASYNC_OK,
	RETRIEVE_ROOT_ASYNC_ERROR,
	REFRESH_ROOT,
	REFRESH_ROOT_ASYNC
}
