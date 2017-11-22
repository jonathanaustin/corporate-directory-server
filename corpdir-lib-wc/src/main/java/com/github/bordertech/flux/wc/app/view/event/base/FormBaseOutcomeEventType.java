package com.github.bordertech.flux.wc.app.view.event.base;

import com.github.bordertech.flux.wc.app.view.event.FormEventType;

/**
 * Form outcome view base events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum FormBaseOutcomeEventType implements FormEventType {
	ADD_OK,
	ADD_ERROR,
	CREATE_OK,
	CREATE_ERROR,
	DELETE_OK,
	DELETE_ERROR,
	LOAD_OK,
	LOAD_ERROR,
	REFRESH_OK,
	REFRESH_ERROR,
	UPDATE_OK,
	UPDATE_ERROR
}
