package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jonathan
 */
public class OrgUnitModel implements SearchModel<String, List<OrgUnit>>, ActionModel<OrgUnit> {

	private static final Log LOG = LogFactory.getLog(OrgUnitModel.class);

	private static final OrgUnitService SERVICE = LocatorUtil.getService(OrgUnitService.class);

	@Override
	public List<OrgUnit> search(final String criteria) {
		try {
			DataResponse<List<OrgUnit>> resp = SERVICE.search(criteria);
			return resp.getData();
		} catch (Throwable e) {
			LOG.error("Error doing search", e);
			throw new SystemException("Error doing search. " + e.getMessage(), e);
		}
	}

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
		return new OrgUnit(null);
	}

}
