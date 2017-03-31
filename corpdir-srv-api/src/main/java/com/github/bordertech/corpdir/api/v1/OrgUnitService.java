package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
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

	ServiceResponse<List<OrgUnit>> getOrgUnits(final String search, final Boolean topLevel);

	ServiceResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId);

	ServiceResponse<OrgUnit> createOrgUnit(final OrgUnit orgUnit);

	ServiceResponse<OrgUnit> updateOrgUnit(final String orgUnitKeyId, final OrgUnit orgUnit);

	ServiceBasicResponse deleteOrgUnit(final String orgUnitKeyId);

	ServiceResponse<List<OrgUnit>> getSubOrgUnits(final String orgUnitKeyId);

	ServiceResponse<OrgUnit> addSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId);

	ServiceResponse<OrgUnit> removeSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId);

	ServiceResponse<List<Position>> getPositions(final String orgUnitKeyId);

	ServiceResponse<OrgUnit> addPosition(final String orgUnitKeyId, final String positionKeyId);

	ServiceResponse<OrgUnit> removePosition(final String orgUnitKeyId, final String positionKeyId);

	ServiceResponse<Position> getManagerPosition(final String orgUnitKeyId);

}
