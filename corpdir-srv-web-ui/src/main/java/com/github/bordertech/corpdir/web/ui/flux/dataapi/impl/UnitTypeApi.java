package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.DefaultCorpCrudDataApi;
import javax.inject.Inject;

/**
 * Unit Type CRUD API implementation.
 *
 * @author jonathan
 */
public class UnitTypeApi extends DefaultCorpCrudDataApi<UnitType, UnitTypeService> {

	@Inject
	public UnitTypeApi(final UnitTypeService service) {
		super(UnitType.class, service);
	}
}
