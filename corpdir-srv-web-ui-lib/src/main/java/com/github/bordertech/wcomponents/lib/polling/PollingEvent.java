package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Polling Service View event.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingEvent implements EventType {
	STARTED,
	ERROR,
	COMPLETE
}
