package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import java.util.List;
import com.github.bordertech.corpdir.api.service.BasicKeyIdService;

/**
 * Organisation Unit Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface UnitTypeService extends BasicKeyIdService<UnitType> {

	DataResponse<List<OrgUnit>> getOrgUnits(final String keyId);

}
