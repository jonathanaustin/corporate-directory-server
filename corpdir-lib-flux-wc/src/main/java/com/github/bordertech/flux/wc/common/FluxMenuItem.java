package com.github.bordertech.flux.wc.common;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.wcomponents.WMenuItem;

/**
 * Custom menu item to hold a view event type.
 *
 * @author jonathan
 */
public class FluxMenuItem extends WMenuItem {

	private final ViewEventType event;
	private final Object data;

	public FluxMenuItem(final String text, final ViewEventType event) {
		this(text, event, null);
	}

	public FluxMenuItem(final String text, final ViewEventType event, final Object data) {
		super(text);
		this.event = event;
		this.data = data;
	}

	public ViewEventType getItemEvent() {
		return event;
	}

	public Object getItemData() {
		return data;
	}

}
