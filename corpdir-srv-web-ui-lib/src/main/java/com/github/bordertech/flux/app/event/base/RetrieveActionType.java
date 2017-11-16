package com.github.bordertech.flux.app.event.base;

import com.github.bordertech.flux.app.event.RetrieveEventType;

/**
 * Clarify the action type to take.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveActionType implements RetrieveEventType {
	CALL_SYNC,
	CALL_ASYNC,
	ASYNC_OK,
	ASYNC_ERROR,
	REFRESH_SYNC,
	REFRESH_ASYNC
}
