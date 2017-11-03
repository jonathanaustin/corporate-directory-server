package com.github.bordertech.wcomponents.lib.app.view.event.base;

import com.github.bordertech.wcomponents.lib.app.view.event.FormViewEvent;

/**
 * Form view base events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum FormBaseViewEvent implements FormViewEvent {
	LOAD_OK,
	LOAD_ERROR,
	ENTITY_MODE_CHANGED,
	RESET_FORM,
	SHOW_FORM
}
