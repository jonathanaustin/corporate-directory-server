package com.github.bordertech.flux.wc.app.view.toolbar;

import com.github.bordertech.flux.wc.app.view.event.ToolbarViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;

/**
 *
 * @author jonathan
 */
public enum ToolbarModelEventItem implements ToolbarItem {
	ADD("Add", ToolbarBaseViewEvent.ADD),
	EDIT("Edit", ToolbarBaseViewEvent.EDIT),
	UPDATE("Update", ToolbarBaseViewEvent.UPDATE),
	CREATE("Create", ToolbarBaseViewEvent.CREATE),
	CANCEL("Cancel", ToolbarBaseViewEvent.CANCEL),
	DELETE("Delete", ToolbarBaseViewEvent.DELETE),
	REFRESH("Refresh", ToolbarBaseViewEvent.REFRESH);

	ToolbarModelEventItem(final String desc, final ToolbarViewEvent eventType) {
		this(desc, eventType, null);
	}

	ToolbarModelEventItem(final String desc, final ToolbarViewEvent eventType, final String imageUrl) {
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
