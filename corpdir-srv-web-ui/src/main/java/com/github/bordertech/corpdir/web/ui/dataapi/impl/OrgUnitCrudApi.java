package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudTreeVersionDataApi;

/**
 *
 * @author jonathan
 */
public class OrgUnitCrudApi extends DefaultCrudTreeVersionDataApi<OrgUnit, OrgUnitService> {

	public OrgUnitCrudApi() {
		super(OrgUnit.class, OrgUnitService.class);
	}

}
