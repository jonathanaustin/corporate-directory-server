package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Input options event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum OptionsEventType implements EventType {
	LOAD_OPTIONS,
	RESET_OPTIONS,
	ADD_OPTION,
	UPDATE_OPTION,
	REMOVE_OPTION
}
