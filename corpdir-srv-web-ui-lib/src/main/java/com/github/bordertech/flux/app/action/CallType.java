package com.github.bordertech.flux.app.action;

/**
 * Clarify the call type to take.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum CallType {
	CALL_SYNC,
	CALL_ASYNC,
	ASYNC_OK,
	ASYNC_ERROR,
	REFRESH_SYNC,
	REFRESH_ASYNC
}
