package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.dataapi.SearchVersionKey;
import com.github.bordertech.flux.crud.dataapi.SearchApi;
import com.github.bordertech.locator.LocatorUtil;
import java.util.List;

/**
 * Contact positions model.
 *
 * @author jonathan
 */
public class ContactPositionsModel implements SearchApi<SearchVersionKey, Position> {

	private static final ContactService SERVICE = LocatorUtil.getService(ContactService.class);

	@Override
	public List<Position> search(final SearchVersionKey criteria) {
		Long versionId = criteria.getVersionId();
		String key = criteria.getId();
		DataResponse<List<Position>> resp;
		if (versionId == null) {
			resp = SERVICE.getPositions(key);
		} else {
			resp = SERVICE.getPositions(versionId, key);
		}
		return resp.getData();
	}

}
