package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.VersionCtrlApi;

/**
 * Version Ctrl CRUD API implementation.
 *
 * @author jonathan
 */
public class VersionCtrlApiImpl extends DefaultCorpCrudApi<VersionCtrl, VersionCtrlService> implements VersionCtrlApi {

	@Inject
	public VersionCtrlApiImpl(final VersionCtrlService service) {
		super(VersionCtrl.class, service);
	}
}
