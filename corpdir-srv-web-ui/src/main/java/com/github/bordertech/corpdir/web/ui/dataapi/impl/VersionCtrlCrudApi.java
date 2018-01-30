package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudDataApi;

/**
 * Version Ctrl Type search and action model.
 *
 * @author jonathan
 */
public class VersionCtrlCrudApi extends DefaultCrudDataApi<VersionCtrl, VersionCtrlService> {

	public VersionCtrlCrudApi() {
		super(VersionCtrl.class, VersionCtrlService.class);
	}
}
