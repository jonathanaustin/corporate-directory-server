package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionApi;

/**
 * Position CRUD API implementation.
 *
 * @author jonathan
 */
public class PositionApiImpl extends DefaultCorpCrudTreeVersionApi<Position, PositionService> implements PositionApi {

	@Inject
	public PositionApiImpl(final PositionService service) {
		super(Position.class, service);
	}

}
