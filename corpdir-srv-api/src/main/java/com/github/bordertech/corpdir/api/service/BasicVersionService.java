package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import java.util.List;

/**
 * Basic service for keyed API object.
 *
 * @param <T> the keyed API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicVersionService<T extends ApiVersionable> extends BasicService<T> {

	DataResponse<List<T>> search(final Long versionId, final String search);

	DataResponse<T> retrieve(final Long versionId, final String keyId);

}
