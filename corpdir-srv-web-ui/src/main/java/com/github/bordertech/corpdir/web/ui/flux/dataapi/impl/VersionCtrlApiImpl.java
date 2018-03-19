package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.VersionCtrlApi;
import javax.inject.Inject;

/**
 * VersionCtrl CRUD API implementation.
 *
 * @author jonathan
 */
public class VersionCtrlApiImpl extends DefaultCorpCrudDataApi<VersionCtrl, VersionCtrlService> implements VersionCtrlApi {

	@Inject
	public VersionCtrlApiImpl(final VersionCtrlService service) {
		super(VersionCtrl.class, service);
	}
}
