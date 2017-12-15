package com.github.bordertech.flux.wc.view.event.base;

import com.github.bordertech.flux.wc.view.event.PollingEventType;

/**
 * Polling view base events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingBaseEventType implements PollingEventType {
	STARTED,
	CHECK_STATUS,
	STOPPED,
	TIMEOUT,
	START_MANUAL,
	START_RETRY,
	START_BUTTON,
	START_AUTO
}
