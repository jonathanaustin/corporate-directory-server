package com.github.bordertech.wcomponents.lib.app.event;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Form events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum FormEventType implements EventType {
	LOAD,
	LOAD_NEW,
	LOAD_OK,
	LOAD_ERROR,
	ENTITY_MODE_CHANGED,
	RESET_FORM,
	SHOW_FORM
}
