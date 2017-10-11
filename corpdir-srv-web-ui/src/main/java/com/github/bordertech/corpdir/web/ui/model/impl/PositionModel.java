package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.model.DefaultModelSearchActionService;

/**
 * Position search and action model.
 *
 * @author jonathan
 */
public class PositionModel extends DefaultModelSearchActionService<Position> {

	public PositionModel() {
		super(Position.class, PositionService.class);
	}
}
