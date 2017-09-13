package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.lib.app.view.ToolbarItem;
import com.github.bordertech.wcomponents.lib.app.event.NavigationEventType;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 *
 * @author jonathan
 */
public enum ToolbarNavigationItem implements ToolbarItem {
	RESET("Reset", NavigationEventType.RESET_VIEW),
	BACK("Back", NavigationEventType.BACK);

	ToolbarNavigationItem(final String desc, final EventType eventType) {
		this(desc, eventType, null);
	}

	ToolbarNavigationItem(final String desc, final EventType eventType, final String imageUrl) {
		this.desc = desc;
		this.eventType = eventType;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final String imageUrl;
	final EventType eventType;

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public EventType getEventType() {
		return eventType;
	}
}
