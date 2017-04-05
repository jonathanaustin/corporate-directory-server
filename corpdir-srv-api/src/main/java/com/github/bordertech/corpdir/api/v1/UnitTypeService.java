package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
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

	DataResponse<List<UnitType>> getUnitTypes(final String search);

	DataResponse<UnitType> getUnitType(final String typeKeyId);

	DataResponse<UnitType> createUnitType(final UnitType type);

	DataResponse<UnitType> updateUnitType(final String typeKeyId, final UnitType type);

	BasicResponse deleteUnitType(final String typeKeyId);

	DataResponse<List<OrgUnit>> getOrgUnits(final String typeKeyId);

}
