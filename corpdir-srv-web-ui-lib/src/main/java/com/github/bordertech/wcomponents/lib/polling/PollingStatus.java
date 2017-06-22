package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.lib.view.ViewMode;

/**
 * Status of the Polling View and if the polling action has been processed successfully.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PollingStatus extends ViewMode {

	/**
	 * Polling not started yet.
	 */
	PollingStatus NOT_STARTED = new PollingStatus() {
	};
	/**
	 * Polling started.
	 */
	PollingStatus STARTED = new PollingStatus() {
	};
	/**
	 * Error occurred.
	 */
	PollingStatus ERROR = new PollingStatus() {
	};
	/**
	 * Polling action completed successfully.
	 */
	PollingStatus COMPLETE = new PollingStatus() {
	};

}
