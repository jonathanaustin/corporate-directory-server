package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.DefaultCorpCrudDataApi;
import javax.inject.Inject;

/**
 * VersionCtrl CRUD API implementation.
 *
 * @author jonathan
 */
public class VersionCtrlApi extends DefaultCorpCrudDataApi<VersionCtrl, VersionCtrlService> {

	@Inject
	public VersionCtrlApi(final VersionCtrlService service) {
		super(VersionCtrl.class, service);
	}
}
