package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import java.util.Date;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitActionModel implements ActionModel<OrgUnit> {

	@Override
	public OrgUnit save(final OrgUnit entity) {
		entity.setDescription("Been in save:" + new Date().toString());
		return entity;
	}

	@Override
	public OrgUnit update(final OrgUnit entity) {
		entity.setDescription("Been in update:" + new Date().toString());
		return entity;
	}

	@Override
	public void delete(final OrgUnit entity) {
		// Do nothing
	}

	@Override
	public OrgUnit refresh(final OrgUnit entity) {
		entity.setDescription("Been in refresh:" + new Date().toString());
		return entity;
	}

	@Override
	public OrgUnit createInstance() {
		return new OrgUnit();
	}

}
