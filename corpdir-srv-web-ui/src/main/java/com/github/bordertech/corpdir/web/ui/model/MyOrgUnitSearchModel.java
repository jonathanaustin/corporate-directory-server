package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.ServiceModel;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jonathan
 */
public class MyOrgUnitSearchModel implements ServiceModel<String, List<OrgUnit>> {

	private static final Log LOG = LogFactory.getLog(MyOrgUnitSearchModel.class);

	private static final OrgUnitService SERVICE = LocatorUtil.getService(OrgUnitService.class);

	@Override
	public List<OrgUnit> service(final String criteria) {
		try {
			DataResponse<List<OrgUnit>> resp = SERVICE.search(criteria);
			return resp.getData();
		} catch (Throwable e) {
			LOG.error("Error doing search", e);
			throw new SystemException("Error doing search. " + e.getMessage(), e);
		}
	}

}
