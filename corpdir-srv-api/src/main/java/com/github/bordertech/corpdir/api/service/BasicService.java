package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import java.util.List;

/**
 * Basic service for keyed API object.
 *
 * @param <T> the keyed API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicService<T extends ApiKeyIdObject> {

	DataResponse<List<T>> search(final String search);

	DataResponse<T> retrieve(final String keyId);

	DataResponse<T> create(final T apiObject);

	DataResponse<T> update(final String keyId, final T apiObject);

	BasicResponse delete(final String keyId);

}
