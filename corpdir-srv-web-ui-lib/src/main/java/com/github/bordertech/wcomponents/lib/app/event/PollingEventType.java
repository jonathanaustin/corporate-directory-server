package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.flux.EventType;

/**
 * Polling Service View event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingEventType implements EventType {
	START_POLLING,
	REFRESH,
	STARTED,
	ERROR,
	COMPLETE,
	RESET_POLLING
}
