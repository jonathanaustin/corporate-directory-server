package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.UnitType;
import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import java.util.List;

/**
 * Organisation Unit Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface UnitTypeService {

	ServiceResponse<List<UnitType>> getUnitTypes();

	ServiceResponse<List<OrgUnit>> getOrgUnits(final String typeKeyId);

	ServiceResponse<UnitType> getUnitType(final String typeKeyId);

	ServiceResponse<UnitType> createUnitType(final UnitType type);

	ServiceResponse<UnitType> updateUnitType(final String typeKeyId, final UnitType type);

	ServiceBasicResponse deleteUnitType(final String typeKeyId);

}
