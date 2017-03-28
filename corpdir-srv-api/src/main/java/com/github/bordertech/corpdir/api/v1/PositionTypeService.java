package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
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

	ServiceResponse<List<PositionType>> getPositionTypes();

	ServiceResponse<List<Position>> getPositions(final String typeKeyId);

	ServiceResponse<PositionType> getPositionType(final String typeKeyId);

	ServiceResponse<PositionType> createPositionType(final PositionType type);

	ServiceResponse<PositionType> updatePositionType(final String typeKeyId, final PositionType type);

	ServiceBasicResponse deletePositionType(final String typeKeyId);

}
