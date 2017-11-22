package com.github.bordertech.flux.wc.app.view.dumb.toolbar;

import com.github.bordertech.flux.wc.app.view.event.ToolbarEventType;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseEventType;

/**
 *
 * @author jonathan
 */
public enum ToolbarNavigationItemType implements ToolbarItem {
	RESET("Reset", ToolbarBaseEventType.RESET),
	BACK("Back", ToolbarBaseEventType.BACK);

	ToolbarNavigationItemType(final String desc, final ToolbarEventType eventType) {
		this(desc, eventType, null);
	}

	ToolbarNavigationItemType(final String desc, final ToolbarEventType eventType, final String imageUrl) {
		this.desc = desc;
		this.eventType = eventType;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final String imageUrl;
	final ToolbarEventType eventType;

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public ToolbarEventType getEventType() {
		return eventType;
	}
}
