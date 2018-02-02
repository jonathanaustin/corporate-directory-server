package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudTreeVersionDataApi;
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
