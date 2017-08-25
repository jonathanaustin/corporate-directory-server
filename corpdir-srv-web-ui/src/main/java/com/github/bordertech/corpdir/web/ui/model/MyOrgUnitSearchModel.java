package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.ServiceModel;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitSearchModel implements ServiceModel<String, List<OrgUnit>> {

	private static final OrgUnitService SERVICE = LocatorUtil.getService(OrgUnitService.class);

	@Override
	public List<OrgUnit> service(final String criteria) {
		DataResponse<List<OrgUnit>> resp = SERVICE.search(criteria);
		return resp.getData();
	}

}
