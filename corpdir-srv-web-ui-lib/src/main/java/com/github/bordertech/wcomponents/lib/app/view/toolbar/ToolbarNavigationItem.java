package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.wcomponents.lib.app.view.event.ToolbarViewEvent;

/**
 *
 * @author jonathan
 */
public enum ToolbarNavigationItem implements ToolbarItem {
	RESET("Reset", ToolbarViewEvent.RESET),
	BACK("Back", ToolbarViewEvent.BACK);

	ToolbarNavigationItem(final String desc, final ViewEventType eventType) {
		this(desc, eventType, null);
	}

	ToolbarNavigationItem(final String desc, final ViewEventType eventType, final String imageUrl) {
		this.desc = desc;
		this.eventType = eventType;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final String imageUrl;
	final ViewEventType eventType;

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public ViewEventType getEventType() {
		return eventType;
	}
}
