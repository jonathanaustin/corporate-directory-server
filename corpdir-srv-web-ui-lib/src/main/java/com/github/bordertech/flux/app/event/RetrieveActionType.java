package com.github.bordertech.flux.app.event;

/**
 * Clarify the action type to take.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveActionType {
	CALL_SYNC,
	CALL_ASYNC,
	ASYNC_OK,
	ASYNC_ERROR,
	REFRESH_SYNC,
	REFRESH_ASYNC
}
