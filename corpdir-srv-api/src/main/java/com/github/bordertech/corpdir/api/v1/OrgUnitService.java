package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;
import com.github.bordertech.corpdir.api.v1.func.PositionFunctions;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;

/**
 * Organisation Unit Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitService extends BasicVersionTreeService<OrgUnit>, PositionFunctions<OrgUnit> {

	DataResponse<Position> getManagerPosition(final String keyId);

	DataResponse<Position> getManagerPosition(final Long versionId, final String keyId);

}
