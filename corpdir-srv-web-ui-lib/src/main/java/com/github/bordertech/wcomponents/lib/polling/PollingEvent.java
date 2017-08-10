package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.lib.pub.Event;

/**
 * Polling Service View event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PollingEvent extends Event {

	PollingEvent STARTED = new PollingEvent() {
	};
	PollingEvent ERROR = new PollingEvent() {
	};
	PollingEvent COMPLETE = new PollingEvent() {
	};
}
