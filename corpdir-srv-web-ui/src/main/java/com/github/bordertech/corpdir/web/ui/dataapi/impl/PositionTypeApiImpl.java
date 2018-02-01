package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;

/**
 * Position Type CRUD API implementation.
 *
 * @author jonathan
 */
public class PositionTypeApiImpl extends DefaultCorpCrudApi<PositionType, PositionTypeService> implements PositionTypeApi {

	@Inject
	public PositionTypeApiImpl(final PositionTypeService service) {
		super(PositionType.class, service);
	}
}
