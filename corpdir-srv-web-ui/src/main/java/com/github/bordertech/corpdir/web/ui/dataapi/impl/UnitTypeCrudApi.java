package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudDataApi;

/**
 * Unit Type search and action model.
 *
 * @author jonathan
 */
public class UnitTypeCrudApi extends DefaultCrudDataApi<UnitType, UnitTypeService> {

	public UnitTypeCrudApi() {
		super(UnitType.class, UnitTypeService.class);
	}
}
