package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import java.util.List;
import com.github.bordertech.corpdir.api.service.BasicKeyIdService;

/**
 * Position Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionTypeService extends BasicKeyIdService<PositionType> {

	DataResponse<List<Position>> getPositions(final String keyId);

}
