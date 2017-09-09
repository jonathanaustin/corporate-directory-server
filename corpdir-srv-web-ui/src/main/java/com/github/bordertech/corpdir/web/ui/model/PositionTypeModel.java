package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import java.util.List;

/**
 * UNit Type action model.
 *
 * @author jonathan
 */
public class PositionTypeModel implements SearchModel<String, List<PositionType>>, ActionModel<PositionType> {

	private static final PositionTypeService SERVICE = LocatorUtil.getService(PositionTypeService.class);

	@Override
	public List<PositionType> search(final String criteria) {
		DataResponse<List<PositionType>> resp = SERVICE.search(criteria);
		return resp.getData();
	}

	@Override
	public PositionType create(final PositionType entity) {
		DataResponse<PositionType> resp = SERVICE.create(entity);
		return resp.getData();
	}

	@Override
	public PositionType update(final PositionType entity) {
		DataResponse<PositionType> resp = SERVICE.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final PositionType entity) {
		SERVICE.delete(entity.getId());
	}

	@Override
	public PositionType refresh(final PositionType entity) {
		DataResponse<PositionType> resp = SERVICE.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public PositionType createInstance() {
		return new PositionType(null);
	}

}
