package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.model.ModelSearchActionService;

/**
 * Org Unit search and action model.
 *
 * @author jonathan
 */
public class OrgUnitModel extends ModelSearchActionService<OrgUnit> {

	public OrgUnitModel() {
		super(OrgUnit.class, OrgUnitService.class);
	}
}
