package com.github.bordertech.corpdir.api.common;

import com.github.bordertech.corpdir.api.response.DataResponse;
import java.util.List;

/**
 * Basic service for Nested API object.
 *
 * @param <T> the nested API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicNestedService<T extends ApiNestedObject> extends BasicService<T> {

	DataResponse<List<T>> getSubs(final String keyId);

	DataResponse<T> addSub(final String keyId, final String subKeyId);

	DataResponse<T> removeSub(final String keyId, final String subKeyId);

}
