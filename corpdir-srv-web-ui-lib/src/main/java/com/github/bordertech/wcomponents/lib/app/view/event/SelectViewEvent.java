package com.github.bordertech.wcomponents.lib.app.view.event;

import com.github.bordertech.flux.event.ViewEventType;

/**
 * Select item in Collection event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum SelectViewEvent implements ViewEventType {
	SELECT,
	UNSELECT
}
