package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;
import com.github.bordertech.corpdir.api.service.BasicTreeVersionService;
import com.github.bordertech.corpdir.api.v1.func.ContactFunctions;

/**
 * Position Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionService extends BasicTreeVersionService<Position>, ContactFunctions<Position> {

	DataResponse<List<OrgUnit>> getManages(final String keyId);

	DataResponse<List<OrgUnit>> getManages(final Integer versionId, final String keyId);

}
