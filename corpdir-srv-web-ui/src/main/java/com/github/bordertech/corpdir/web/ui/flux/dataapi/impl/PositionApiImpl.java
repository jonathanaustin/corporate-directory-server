package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.PositionApi;
import javax.inject.Inject;

/**
 * Position CRUD API implementation.
 *
 * @author jonathan
 */
public class PositionApiImpl extends DefaultCorpCrudTreeVersionDataApi<Position, PositionService> implements PositionApi {

	@Inject
	public PositionApiImpl(final PositionService service) {
		super(Position.class, service);
	}

}
