package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitActionModel implements ActionModel<OrgUnit> {

	@Override
	public OrgUnit create(final OrgUnit entity) {
		entity.setId(UUID.randomUUID().toString());
		return entity;
	}

	@Override
	public OrgUnit update(final OrgUnit entity) {
		return entity;
	}

	@Override
	public void delete(final OrgUnit entity) {
		// Do nothing
	}

	@Override
	public OrgUnit refresh(final OrgUnit entity) {
		return entity;
	}

	@Override
	public OrgUnit createInstance() {
		return new OrgUnit();
	}

}
