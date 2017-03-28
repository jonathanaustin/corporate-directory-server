package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import java.util.List;

/**
 * Organisation Unit Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface UnitTypeService {

	ServiceResponse<List<UnitType>> getUnitTypes(final String search);

	ServiceResponse<List<OrgUnit>> getOrgUnits(final String typeKeyId);

	ServiceResponse<UnitType> getUnitType(final String typeKeyId);

	ServiceResponse<UnitType> createUnitType(final UnitType type);

	ServiceResponse<UnitType> updateUnitType(final String typeKeyId, final UnitType type);

	ServiceBasicResponse deleteUnitType(final String typeKeyId);

}
