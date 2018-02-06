package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionKeyIdService;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudVersionDataApi;
import java.util.List;

/**
 * CRUD Tree API calling CorpDir Services.
 *
 * @param <T> the CorpDir Treeable API object
 * @param <S> the CorpDir tree service type
 *
 * @author jonathan
 */
public class DefaultCorpCrudVersionDataApi<T extends ApiVersionable, S extends BasicVersionKeyIdService<T>> extends DefaultCorpCrudDataApi<T, S> implements CorpCrudVersionDataApi<T, S> {

	public DefaultCorpCrudVersionDataApi(final Class<T> apiClass, final S service) {
		super(apiClass, service);
	}

	@Override
	public T retrieve(final Long versionId, final String key) {
		DataResponse<T> resp = getService().retrieve(versionId, key);
		return resp.getData();
	}

	@Override
	public List<T> search(final Long versionId, final String criteria) {
		DataResponse<List<T>> resp = getService().search(versionId, criteria);
		return resp.getData();
	}

}
