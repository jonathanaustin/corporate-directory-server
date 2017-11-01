package com.github.bordertech.wcomponents.lib.app.common;

import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.flux.event.ViewEventType;

/**
 *
 * @author jonathan
 */
public class AppMenuItem extends WMenuItem {

	private final ViewEventType event;
	private final Object data;

	public AppMenuItem(final String text, final ViewEventType event) {
		this(text, event, null);
	}

	public AppMenuItem(final String text, final ViewEventType event, final Object data) {
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
