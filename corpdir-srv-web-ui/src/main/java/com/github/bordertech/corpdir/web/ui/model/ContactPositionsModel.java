package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.SearchModel;
import java.util.List;

/**
 * Contact positions model.
 *
 * @author jonathan
 */
public class ContactPositionsModel implements SearchModel<SearchVersionKey, List<Position>> {

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
