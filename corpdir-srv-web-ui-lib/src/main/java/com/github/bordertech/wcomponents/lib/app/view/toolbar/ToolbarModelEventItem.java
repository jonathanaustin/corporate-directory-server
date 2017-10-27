package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.flux.EventType;

/**
 *
 * @author jonathan
 */
public enum ToolbarModelEventItem implements ToolbarItem {
	ADD("Add", ModelEventType.ADD),
	EDIT("Edit", ModelEventType.EDIT),
	UPDATE("Update", ModelEventType.UPDATE),
	CREATE("Create", ModelEventType.CREATE),
	CANCEL("Cancel", ModelEventType.CANCEL),
	DELETE("Delete", ModelEventType.DELETE),
	REFRESH("Refresh", ModelEventType.REFRESH);

	ToolbarModelEventItem(final String desc, final EventType eventType) {
		this(desc, eventType, null);
	}

	ToolbarModelEventItem(final String desc, final EventType eventType, final String imageUrl) {
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
