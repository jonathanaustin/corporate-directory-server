package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
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
public interface PositionTypeService {

	DataResponse<List<PositionType>> getPositionTypes(final String search);

	DataResponse<PositionType> getPositionType(final String typeKeyId);

	DataResponse<PositionType> createPositionType(final PositionType type);

	DataResponse<PositionType> updatePositionType(final String typeKeyId, final PositionType type);

	BasicResponse deletePositionType(final String typeKeyId);

	DataResponse<List<Position>> getPositions(final String typeKeyId);

}
