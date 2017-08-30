package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import java.util.List;

/**
 * Basic service for Tree API object.
 *
 * @param <T> the tree API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicTreeService<T extends ApiTreeable & ApiKeyIdObject> extends BasicService<T> {

	DataResponse<List<T>> getSubs(final String keyId);

	DataResponse<T> addSub(final String keyId, final String subKeyId);

	DataResponse<T> removeSub(final String keyId, final String subKeyId);

}
