package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudDataApi;

/**
 * Position Type search and action model.
 *
 * @author jonathan
 */
public class PositionTypeCrudApi extends DefaultCrudDataApi<PositionType, PositionTypeService> {

	public PositionTypeCrudApi() {
		super(PositionType.class, PositionTypeService.class);
	}
}
