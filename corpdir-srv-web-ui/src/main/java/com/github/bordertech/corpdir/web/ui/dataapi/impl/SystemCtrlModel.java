package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.SystemCtrlService;
import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelSearchActionService;

/**
 * System Ctrl Type search and action model.
 *
 * @author jonathan
 */
public class SystemCtrlModel extends DefaultModelSearchActionService<SystemCtrl, SystemCtrlService> {

	public SystemCtrlModel() {
		super(SystemCtrl.class, SystemCtrlService.class);
	}
}
