package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.SystemCtrlService;
import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.SystemCtrlApi;

/**
 * System Ctrl CRUD API implementation.
 *
 * @author jonathan
 */
public class SystemCtrlApiImpl extends DefaultCorpCrudApi<SystemCtrl, SystemCtrlService> implements SystemCtrlApi {

	@Inject
	public SystemCtrlApiImpl(final SystemCtrlService service) {
		super(SystemCtrl.class, service);
	}
}
