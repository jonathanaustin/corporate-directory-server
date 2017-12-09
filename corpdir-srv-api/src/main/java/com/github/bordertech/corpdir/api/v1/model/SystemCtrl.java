package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.DefaultIdObject;

/**
 * System Control Record.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SystemCtrl extends DefaultIdObject {

	private String currentVersionId;

	protected SystemCtrl() {
	}

	public SystemCtrl(final String id) {
		super(id);
	}

	public String getCurrentVersionId() {
		return currentVersionId;
	}

	public void setCurrentVersionId(final String currentVersionId) {
		this.currentVersionId = currentVersionId;
	}

}
