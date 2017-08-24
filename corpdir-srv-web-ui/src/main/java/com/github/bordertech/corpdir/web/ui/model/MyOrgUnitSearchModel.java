package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.wcomponents.lib.model.ServiceModel;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitSearchModel implements ServiceModel<String, List<OrgUnit>> {

	@Inject
	private static OrgUnitService impl;

	@Override
	public List<OrgUnit> service(final String criteria) {
		DataResponse<List<OrgUnit>> resp = impl.search(criteria);
		return resp.getData();
	}

}
