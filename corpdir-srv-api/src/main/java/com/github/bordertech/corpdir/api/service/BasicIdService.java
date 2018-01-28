package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import java.io.Serializable;
import java.util.List;

/**
 * Basic service for ID API object.
 *
 * @param <T> the keyed API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicIdService<T extends ApiIdObject> extends Serializable {

	DataResponse<List<T>> search(final String search);

	DataResponse<T> retrieve(final String id);

	DataResponse<T> create(final T apiObject);

	DataResponse<T> update(final String id, final T apiObject);

	BasicResponse delete(final String id);

}
