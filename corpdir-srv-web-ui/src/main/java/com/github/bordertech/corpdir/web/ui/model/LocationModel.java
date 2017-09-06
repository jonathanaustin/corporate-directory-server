package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.SearchModel;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jonathan
 */
public class LocationModel implements SearchModel<String, List<Location>>, ActionModel<Location> {

	private static final Log LOG = LogFactory.getLog(LocationModel.class);

	private static final LocationService SERVICE = LocatorUtil.getService(LocationService.class);

	@Override
	public List<Location> search(final String criteria) {
		try {
			DataResponse<List<Location>> resp = SERVICE.search(criteria);
			return resp.getData();
		} catch (Throwable e) {
			LOG.error("Error doing search", e);
			throw new SystemException("Error doing search. " + e.getMessage(), e);
		}
	}

	@Override
	public Location create(final Location entity) {
		DataResponse<Location> resp = SERVICE.create(entity);
		return resp.getData();
	}

	@Override
	public Location update(final Location entity) {
		DataResponse<Location> resp = SERVICE.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final Location entity) {
		SERVICE.delete(entity.getId());
	}

	@Override
	public Location refresh(final Location entity) {
		DataResponse<Location> resp = SERVICE.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public Location createInstance() {
		return new Location(null);
	}

}
