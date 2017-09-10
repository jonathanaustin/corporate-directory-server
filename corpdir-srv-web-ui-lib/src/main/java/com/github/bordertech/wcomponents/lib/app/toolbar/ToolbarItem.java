package com.github.bordertech.wcomponents.lib.app.toolbar;

import com.github.bordertech.wcomponents.lib.app.view.ToolbarType;

/**
 *
 * @author jonathan
 */
public enum ToolbarItem implements ToolbarType {
	ADD("Add"),
	RESET("Reset"),
	BACK("Back"),
	EDIT("Edit"),
	UPDATE("Update"),
	CREATE("Create"),
	CANCEL("Cancel"),
	DELETE("Delete");

	ToolbarItem(final String desc) {
		this(desc, null);
	}

	ToolbarItem(final String desc, final String imageUrl) {
		this.desc = desc;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final String imageUrl;

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}
}
