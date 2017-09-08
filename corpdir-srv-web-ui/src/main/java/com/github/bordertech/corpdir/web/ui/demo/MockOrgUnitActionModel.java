package com.github.bordertech.corpdir.web.ui.demo;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class MockOrgUnitActionModel implements ActionModel<OrgUnit> {

	@Override
	public OrgUnit create(final OrgUnit entity) {
		OrgUnit create = new OrgUnit(UUID.randomUUID().toString());
		create.setActive(entity.isActive());
		create.setCustom(entity.isCustom());
		create.setTimestamp(entity.getTimestamp());
		create.setBusinessKey(entity.getBusinessKey());
		create.setDescription(entity.getDescription());
		create.setManagerPosId(entity.getManagerPosId());
		create.setParentId(entity.getParentId());
		create.setPositionIds(entity.getPositionIds());
		create.setTypeId(entity.getTypeId());
		create.setVersionId(entity.getVersionId());
		return create;
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
		return new OrgUnit(null);
	}

}
