package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.UnitTypeApi;
import javax.inject.Inject;

/**
 * Unit Type CRUD API implementation.
 *
 * @author jonathan
 */
public class UnitTypeApiImpl extends DefaultCorpCrudDataApi<UnitType, UnitTypeService> implements UnitTypeApi {

	@Inject
	public UnitTypeApiImpl(final UnitTypeService service) {
		super(UnitType.class, service);
	}
}
