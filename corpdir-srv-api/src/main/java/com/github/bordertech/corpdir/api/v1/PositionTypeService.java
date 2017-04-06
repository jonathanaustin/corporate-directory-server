package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.common.BasicService;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import java.util.List;

/**
 * Position Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionTypeService extends BasicService<PositionType> {

	DataResponse<List<Position>> getPositions(final String keyId);

}
