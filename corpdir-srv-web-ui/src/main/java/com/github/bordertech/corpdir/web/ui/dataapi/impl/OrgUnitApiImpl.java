package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.DefaultCorpCrudTreeVersionDataApi;
import javax.inject.Inject;

/**
 * OrgUnit CRUD API implementation.
 *
 * @author jonathan
 */
public class OrgUnitApiImpl extends DefaultCorpCrudTreeVersionDataApi<OrgUnit, OrgUnitService> implements OrgUnitApi {

	@Inject
	public OrgUnitApiImpl(final OrgUnitService service) {
		super(OrgUnit.class, service);
	}

}
