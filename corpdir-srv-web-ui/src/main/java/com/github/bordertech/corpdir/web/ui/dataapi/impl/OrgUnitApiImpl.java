package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;

/**
 * OrgUnit CRUD API implementation.
 *
 * @author jonathan
 */
public class OrgUnitApiImpl extends DefaultCorpCrudTreeVersionApi<OrgUnit, OrgUnitService> implements OrgUnitApi {

	@Inject
	public OrgUnitApiImpl(final OrgUnitService service) {
		super(OrgUnit.class, service);
	}

}
