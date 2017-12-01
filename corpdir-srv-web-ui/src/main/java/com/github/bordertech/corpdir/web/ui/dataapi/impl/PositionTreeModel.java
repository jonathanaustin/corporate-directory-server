package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultModelTreeVersionService;

/**
 *
 * @author jonathan
 */
public class PositionTreeModel extends DefaultModelTreeVersionService<Position, PositionService> {

	public PositionTreeModel() {
		super(Position.class, PositionService.class);
	}

}
