package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.DefaultCorpCrudDataApi;
import javax.inject.Inject;

/**
 * PositionType CRUD API implementation.
 *
 * @author jonathan
 */
public class PositionTypeApi extends DefaultCorpCrudDataApi<PositionType, PositionTypeService> {

	@Inject
	public PositionTypeApi(final PositionTypeService service) {
		super(PositionType.class, service);
	}
}
