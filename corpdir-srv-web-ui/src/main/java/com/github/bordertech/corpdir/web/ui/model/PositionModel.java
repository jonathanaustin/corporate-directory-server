package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
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
public class PositionModel implements SearchModel<String, List<Position>>, ActionModel<Position> {

	private static final Log LOG = LogFactory.getLog(PositionModel.class);

	private static final PositionService SERVICE = LocatorUtil.getService(PositionService.class);

	@Override
	public List<Position> search(final String criteria) {
		try {
			DataResponse<List<Position>> resp = SERVICE.search(criteria);
			return resp.getData();
		} catch (Throwable e) {
			LOG.error("Error doing search", e);
			throw new SystemException("Error doing search. " + e.getMessage(), e);
		}
	}

	@Override
	public Position create(final Position entity) {
		DataResponse<Position> resp = SERVICE.create(entity);
		return resp.getData();
	}

	@Override
	public Position update(final Position entity) {
		DataResponse<Position> resp = SERVICE.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final Position entity) {
		SERVICE.delete(entity.getId());
	}

	@Override
	public Position refresh(final Position entity) {
		DataResponse<Position> resp = SERVICE.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public Position createInstance() {
		return new Position(null);
	}

}
