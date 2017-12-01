package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelSearchActionService;

/**
 * Unit Type search and action model.
 *
 * @author jonathan
 */
public class UnitTypeModel extends DefaultModelSearchActionService<UnitType, UnitTypeService> {

	public UnitTypeModel() {
		super(UnitType.class, UnitTypeService.class);
	}
}
