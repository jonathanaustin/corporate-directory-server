package com.github.bordertech.wcomponents.lib.app.view.event;

import com.github.bordertech.flux.event.ViewEventType;

/**
 * Polling Service View event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingViewEvent implements ViewEventType {
	START_POLLING,
	REFRESH,
	STARTED,
	ERROR,
	COMPLETE,
	RESET_POLLING
}
