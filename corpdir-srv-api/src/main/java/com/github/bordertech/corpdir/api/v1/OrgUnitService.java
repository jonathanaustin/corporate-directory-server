package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;

/**
 * Organisation Unit Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitService {

	DataResponse<List<OrgUnit>> getOrgUnits(final String search, final Boolean assigned);

	DataResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId);

	DataResponse<OrgUnit> createOrgUnit(final OrgUnit orgUnit);

	DataResponse<OrgUnit> updateOrgUnit(final String orgUnitKeyId, final OrgUnit orgUnit);

	BasicResponse deleteOrgUnit(final String orgUnitKeyId);

	DataResponse<List<OrgUnit>> getSubOrgUnits(final String orgUnitKeyId);

	DataResponse<OrgUnit> addSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId);

	DataResponse<OrgUnit> removeSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId);

	DataResponse<List<Position>> getPositions(final String orgUnitKeyId);

	DataResponse<OrgUnit> addPosition(final String orgUnitKeyId, final String positionKeyId);

	DataResponse<OrgUnit> removePosition(final String orgUnitKeyId, final String positionKeyId);

	DataResponse<Position> getManagerPosition(final String orgUnitKeyId);

}
