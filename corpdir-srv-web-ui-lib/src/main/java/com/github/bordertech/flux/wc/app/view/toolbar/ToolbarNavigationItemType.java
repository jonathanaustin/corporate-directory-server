package com.github.bordertech.flux.wc.app.view.toolbar;

import com.github.bordertech.flux.wc.app.view.event.ToolbarViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;

/**
 *
 * @author jonathan
 */
public enum ToolbarNavigationItemType implements ToolbarItem {
	RESET("Reset", ToolbarBaseViewEvent.RESET),
	BACK("Back", ToolbarBaseViewEvent.BACK);

	ToolbarNavigationItemType(final String desc, final ToolbarViewEvent eventType) {
		this(desc, eventType, null);
	}

	ToolbarNavigationItemType(final String desc, final ToolbarViewEvent eventType, final String imageUrl) {
		this.desc = desc;
		this.eventType = eventType;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final String imageUrl;
	final ToolbarViewEvent eventType;

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public ToolbarViewEvent getEventType() {
		return eventType;
	}
}
