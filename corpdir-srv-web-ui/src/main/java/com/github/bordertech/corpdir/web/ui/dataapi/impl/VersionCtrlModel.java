package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelSearchActionService;

/**
 * Version Ctrl Type search and action model.
 *
 * @author jonathan
 */
public class VersionCtrlModel extends DefaultModelSearchActionService<VersionCtrl, VersionCtrlService> {

	public VersionCtrlModel() {
		super(VersionCtrl.class, VersionCtrlService.class);
	}
}
