package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.DefaultCorpCrudDataApi;
import javax.inject.Inject;

/**
 * PositionType CRUD API implementation.
 *
 * @author jonathan
 */
public class PositionTypeApiImpl extends DefaultCorpCrudDataApi<PositionType, PositionTypeService> implements PositionTypeApi {

	@Inject
	public PositionTypeApiImpl(final PositionTypeService service) {
		super(PositionType.class, service);
	}
}
