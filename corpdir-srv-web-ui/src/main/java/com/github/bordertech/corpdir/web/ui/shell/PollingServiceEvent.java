package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Polling Service View event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PollingServiceEvent extends ViewEvent {

	PollingServiceEvent LOADED = new PollingServiceEvent() {
	};
}
