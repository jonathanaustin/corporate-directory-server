package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.SearchModel;
import java.util.List;

/**
 * UNit Type action model.
 *
 * @author jonathan
 */
public class UnitTypeModel implements SearchModel<String, List<UnitType>>, ActionModel<UnitType> {

	private static final UnitTypeService SERVICE = LocatorUtil.getService(UnitTypeService.class);

	@Override
	public List<UnitType> search(final String criteria) {
		DataResponse<List<UnitType>> resp = SERVICE.search(criteria);
		return resp.getData();
	}

	@Override
	public UnitType create(final UnitType entity) {
		DataResponse<UnitType> resp = SERVICE.create(entity);
		return resp.getData();
	}

	@Override
	public UnitType update(final UnitType entity) {
		DataResponse<UnitType> resp = SERVICE.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final UnitType entity) {
		SERVICE.delete(entity.getId());
	}

	@Override
	public UnitType refresh(final UnitType entity) {
		DataResponse<UnitType> resp = SERVICE.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public UnitType createInstance() {
		return new UnitType(null);
	}

}
