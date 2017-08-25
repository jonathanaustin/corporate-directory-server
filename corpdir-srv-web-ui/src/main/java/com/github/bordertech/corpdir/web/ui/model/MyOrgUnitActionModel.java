package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.ActionModel;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitActionModel implements ActionModel<OrgUnit> {

	private static final OrgUnitService SERVICE = LocatorUtil.getService(OrgUnitService.class);

	@Override
	public OrgUnit create(final OrgUnit entity) {
		DataResponse<OrgUnit> resp = SERVICE.create(entity);
		return resp.getData();
	}

	@Override
	public OrgUnit update(final OrgUnit entity) {
		DataResponse<OrgUnit> resp = SERVICE.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final OrgUnit entity) {
		SERVICE.delete(entity.getId());
	}

	@Override
	public OrgUnit refresh(final OrgUnit entity) {
		DataResponse<OrgUnit> resp = SERVICE.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public OrgUnit createInstance() {
		return new OrgUnit();
	}

}
