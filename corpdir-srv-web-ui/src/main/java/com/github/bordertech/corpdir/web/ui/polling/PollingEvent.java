package com.github.bordertech.corpdir.web.ui.polling;

import com.github.bordertech.corpdir.web.ui.shell.ViewEvent;

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
