package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.Position;
import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import java.util.List;

/**
 * Organisation Unit Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitService {

	ServiceResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId);

	ServiceResponse<List<OrgUnit>> getSubOrgUnits(final String orgUnitKeyId);

	ServiceResponse<List<Position>> getAssignedPositions(final String orgUnitKeyId);

	ServiceResponse<Position> getOrgUnitManager(final String orgUnitKeyId);

	ServiceResponse<String> createOrgUnit(final OrgUnit orgUnit);

	ServiceResponse<OrgUnit> updateOrgUnit(final String orgUnitKeyId, final OrgUnit orgUnit);

	ServiceBasicResponse deleteOrgUnit(final String orgUnitKeyId);

	ServiceBasicResponse assignOrgUnitToOrgUnit(final String orgUnitKeyId, final String parentOrgUnitKeyId);

	ServiceBasicResponse assignPosition(final String orgUnitKeyId, final String positionKeyId);

	ServiceBasicResponse unassignPosition(final String orgUnitKeyId, final String positionKeyId);

}
