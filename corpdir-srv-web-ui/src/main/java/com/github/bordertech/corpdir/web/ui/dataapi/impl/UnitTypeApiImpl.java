package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.UnitTypeApi;

/**
 * Unit Type CRUD API implementation.
 *
 * @author jonathan
 */
public class UnitTypeApiImpl extends DefaultCorpCrudApi<UnitType, UnitTypeService> implements UnitTypeApi {

	@Inject
	public UnitTypeApiImpl(final UnitTypeService service) {
		super(UnitType.class, service);
	}
}
