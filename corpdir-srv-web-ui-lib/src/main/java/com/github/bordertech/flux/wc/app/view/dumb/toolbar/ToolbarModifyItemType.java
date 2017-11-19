package com.github.bordertech.flux.wc.app.view.dumb.toolbar;

import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.flux.wc.app.view.event.ToolbarEventType;

/**
 *
 * @author jonathan
 */
public enum ToolbarModifyItemType implements ToolbarItem {
	ADD("Add", ToolbarBaseEventType.ADD),
	EDIT("Edit", ToolbarBaseEventType.EDIT),
	UPDATE("Update", ToolbarBaseEventType.UPDATE),
	CREATE("Create", ToolbarBaseEventType.CREATE),
	CANCEL("Cancel", ToolbarBaseEventType.CANCEL),
	DELETE("Delete", ToolbarBaseEventType.DELETE),
	REFRESH("Refresh", ToolbarBaseEventType.REFRESH);

	ToolbarModifyItemType(final String desc, final ToolbarEventType eventType) {
		this(desc, eventType, null);
	}

	ToolbarModifyItemType(final String desc, final ToolbarEventType eventType, final String imageUrl) {
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
