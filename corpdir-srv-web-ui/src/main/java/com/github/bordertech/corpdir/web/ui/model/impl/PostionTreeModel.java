package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.model.DefaultModelTreeVersionService;

/**
 *
 * @author jonathan
 */
public class PostionTreeModel extends DefaultModelTreeVersionService<Position> {

	public PostionTreeModel() {
		super(PositionService.class);
	}

}
