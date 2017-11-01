package com.github.bordertech.wcomponents.lib.app.view.event;

import com.github.bordertech.flux.event.ViewEventType;

/**
 * Form events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum FormViewEvent implements ViewEventType {
	LOAD_OK,
	LOAD_ERROR,
	ENTITY_MODE_CHANGED,
	RESET_FORM,
	SHOW_FORM
}
