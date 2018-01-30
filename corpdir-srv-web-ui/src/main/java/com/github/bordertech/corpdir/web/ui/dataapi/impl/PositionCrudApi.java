package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudTreeVersionDataApi;

/**
 *
 * @author jonathan
 */
public class PositionCrudApi extends DefaultCrudTreeVersionDataApi<Position, PositionService> {

	public PositionCrudApi() {
		super(Position.class, PositionService.class);
	}

}
