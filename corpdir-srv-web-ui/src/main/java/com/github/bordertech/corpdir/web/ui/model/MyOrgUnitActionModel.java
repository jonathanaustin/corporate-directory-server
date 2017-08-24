package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import javax.inject.Inject;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitActionModel implements ActionModel<OrgUnit> {

	@Inject
	private static OrgUnitService impl;

	@Override
	public OrgUnit create(final OrgUnit entity) {
		DataResponse<OrgUnit> resp = impl.create(entity);
		return resp.getData();
	}

	@Override
	public OrgUnit update(final OrgUnit entity) {
		DataResponse<OrgUnit> resp = impl.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final OrgUnit entity) {
		impl.delete(entity.getId());
	}

	@Override
	public OrgUnit refresh(final OrgUnit entity) {
		DataResponse<OrgUnit> resp = impl.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public OrgUnit createInstance() {
		return new OrgUnit();
	}

}
