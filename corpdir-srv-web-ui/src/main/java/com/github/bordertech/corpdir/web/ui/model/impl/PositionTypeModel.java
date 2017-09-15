package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.model.ModelSearchActionService;

/**
 * Position Type search and action model.
 *
 * @author jonathan
 */
public class PositionTypeModel extends ModelSearchActionService<PositionType> {

	public PositionTypeModel() {
		super(PositionType.class, PositionTypeService.class);
	}
}
