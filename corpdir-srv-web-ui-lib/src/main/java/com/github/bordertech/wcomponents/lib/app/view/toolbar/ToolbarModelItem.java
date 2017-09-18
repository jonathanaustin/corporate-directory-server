package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 *
 * @author jonathan
 */
public enum ToolbarModelItem implements ToolbarItem {
	ADD("Add", ModelEventType.ADD),
	EDIT("Edit", ModelEventType.EDIT),
	UPDATE("Update", ModelEventType.UPDATE),
	CREATE("Create", ModelEventType.CREATE),
	CANCEL("Cancel", ModelEventType.CANCEL),
	DELETE("Delete", ModelEventType.DELETE);

	ToolbarModelItem(final String desc, final EventType eventType) {
		this(desc, eventType, null);
	}

	ToolbarModelItem(final String desc, final EventType eventType, final String imageUrl) {
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
