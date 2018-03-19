package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.DefaultCorpCrudTreeVersionDataApi;
import javax.inject.Inject;

/**
 * Position CRUD API implementation.
 *
 * @author jonathan
 */
public class PositionApi extends DefaultCorpCrudTreeVersionDataApi<Position, PositionService> {

	@Inject
	public PositionApi(final PositionService service) {
		super(Position.class, service);
	}

}
