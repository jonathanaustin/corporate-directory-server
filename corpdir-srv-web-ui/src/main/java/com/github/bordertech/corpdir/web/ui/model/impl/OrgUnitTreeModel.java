package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.model.DefaultModelTreeVersionService;

/**
 *
 * @author jonathan
 */
public class OrgUnitTreeModel extends DefaultModelTreeVersionService<OrgUnit> {

	public OrgUnitTreeModel() {
		super(OrgUnitService.class);
	}

}
