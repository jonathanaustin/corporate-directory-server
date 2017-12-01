package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelSearchActionService;

/**
 * Position Type search and action model.
 *
 * @author jonathan
 */
public class PositionTypeModel extends DefaultModelSearchActionService<PositionType, PositionTypeService> {

	public PositionTypeModel() {
		super(PositionType.class, PositionTypeService.class);
	}
}
