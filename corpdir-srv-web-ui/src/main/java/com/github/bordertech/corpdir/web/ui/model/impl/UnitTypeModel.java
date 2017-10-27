package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.model.DefaultModelSearchActionService;

/**
 * Unit Type search and action model.
 *
 * @author jonathan
 */
public class UnitTypeModel extends DefaultModelSearchActionService<UnitType> {

	public UnitTypeModel() {
		super(UnitType.class, UnitTypeService.class);
	}
}
