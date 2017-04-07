package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;
import com.github.bordertech.corpdir.api.common.BasicTreeService;

/**
 * Organisation Unit Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitService extends BasicTreeService<OrgUnit> {

	DataResponse<List<Position>> getPositions(final String keyId);

	DataResponse<OrgUnit> addPosition(final String keyId, final String positionKeyId);

	DataResponse<OrgUnit> removePosition(final String keyId, final String positionKeyId);

	DataResponse<Position> getManagerPosition(final String keyId);

}
