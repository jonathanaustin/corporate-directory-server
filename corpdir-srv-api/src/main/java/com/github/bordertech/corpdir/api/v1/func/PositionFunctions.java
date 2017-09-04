package com.github.bordertech.corpdir.api.v1.func;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.io.Serializable;
import java.util.List;

/**
 * Position functions.
 *
 * @author Jonathan Austin
 * @param <T> the type having positions added to it
 * @since 1.0.0
 */
public interface PositionFunctions<T extends ApiVersionable> extends Serializable {

	DataResponse<List<Position>> getPositions(final String keyId);

	DataResponse<T> addPosition(final String keyId, final String positionKeyId);

	DataResponse<T> removePosition(final String keyId, final String positionKeyId);

	DataResponse<List<Position>> getPositions(final Long versionId, final String keyId);

	DataResponse<T> addPosition(final Long versionId, final String keyId, final String positionKeyId);

	DataResponse<T> removePosition(final Long versionId, final String keyId, final String positionKeyId);

}
