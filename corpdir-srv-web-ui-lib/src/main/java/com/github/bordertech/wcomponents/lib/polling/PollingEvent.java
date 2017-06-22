package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.lib.view.ViewEvent;

/**
 * Polling Service View event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PollingEvent extends ViewEvent {

	PollingEvent STARTED = new PollingEvent() {
	};
	PollingEvent ERROR = new PollingEvent() {
	};
	PollingEvent COMPLETE = new PollingEvent() {
	};
}
