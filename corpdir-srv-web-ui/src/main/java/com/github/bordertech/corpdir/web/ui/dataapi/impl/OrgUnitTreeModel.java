package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelTreeVersionService;

/**
 *
 * @author jonathan
 */
public class OrgUnitTreeModel extends DefaultModelTreeVersionService<OrgUnit, OrgUnitService> {

	public OrgUnitTreeModel() {
		super(OrgUnit.class, OrgUnitService.class);
	}

}
