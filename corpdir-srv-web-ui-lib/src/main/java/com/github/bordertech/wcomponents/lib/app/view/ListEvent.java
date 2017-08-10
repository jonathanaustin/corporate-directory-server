package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * List events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ListEvent implements EventType {
	VIEW,
	EDIT,
	DELETE,
	SELECT
}
