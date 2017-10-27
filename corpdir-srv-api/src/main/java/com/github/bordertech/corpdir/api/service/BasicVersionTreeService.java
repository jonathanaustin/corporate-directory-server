package com.github.bordertech.corpdir.api.service;

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
public interface BasicVersionTreeService<T extends ApiTreeable & ApiVersionable> extends BasicVersionService<T>, BasicTreeService<T> {

	DataResponse<List<T>> getSubs(final Long versionId, final String keyId);

	DataResponse<T> addSub(final Long versionId, final String keyId, final String subKeyId);

	DataResponse<T> removeSub(final Long versionId, final String keyId, final String subKeyId);

	DataResponse<List<T>> getRootItems(final Long versionId);

}
