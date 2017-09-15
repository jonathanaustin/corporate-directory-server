package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.model.ModelSearchActionService;

/**
 * Position search and action model.
 *
 * @author jonathan
 */
public class PositionModel extends ModelSearchActionService<Position> {

	public PositionModel() {
		super(Position.class, PositionService.class);
	}
}
