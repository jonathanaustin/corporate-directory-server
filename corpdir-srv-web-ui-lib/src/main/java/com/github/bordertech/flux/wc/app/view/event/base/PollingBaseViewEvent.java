package com.github.bordertech.flux.wc.app.view.event.base;

import com.github.bordertech.flux.wc.app.view.event.PollingViewEvent;

/**
 * Polling view base events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingBaseViewEvent implements PollingViewEvent {
	STARTED,
	CHECK_STATUS,
	STOPPED,
	START_RETRY,
	START_BUTTON,
	START_AUTO
}
