package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import java.util.List;

/**
 * Basic service for Tree API Object with a version.
 *
 * @param <T> the tree API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicTreeVersionService<T extends ApiTreeable & ApiVersionable & ApiKeyIdObject> extends BasicTreeService<T> {

	DataResponse<List<T>> getSubs(final Integer versionId, final String keyId);

	DataResponse<T> addSub(final Integer versionId, final String keyId, final String subKeyId);

	DataResponse<T> removeSub(final Integer versionId, final String keyId, final String subKeyId);

}
