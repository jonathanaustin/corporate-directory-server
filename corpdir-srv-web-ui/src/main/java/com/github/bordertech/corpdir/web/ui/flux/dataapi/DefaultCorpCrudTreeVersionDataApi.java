package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;
import java.util.List;

/**
 * CRUD Tree Version API calling CorpDir Services.
 *
 * @param <T> the treeable API type
 * @param <S> the versionable tree service type
 *
 * @author jonathan
 */
public class DefaultCorpCrudTreeVersionDataApi<T extends ApiTreeable & ApiVersionable, S extends BasicVersionTreeService<T>> extends DefaultCorpCrudTreeDataApi<T, S> implements CorpCrudTreeVersionDataApi<T, S> {

	public DefaultCorpCrudTreeVersionDataApi(final Class<T> apiClass, final S service) {
		super(apiClass, service);
	}

	@Override
	public boolean hasChildren(final Long versionId, final T item) {
		return !item.getSubIds().isEmpty();
	}

	@Override
	public List<T> getChildren(final Long versionId, final T item) {
		DataResponse<List<T>> resp = getService().getSubs(versionId, item.getId());
		return resp.getData();
	}

	@Override
	public List<T> getRootItems(final Long versionId) {
		DataResponse<List<T>> resp = getService().getRootItems(versionId);
		return resp.getData();
	}

	@Override
	public T addChild(final Long versionId, final T parent, final T child) {
		DataResponse<T> resp = getService().addSub(versionId, parent.getId(), child.getId());
		return resp.getData();
	}

	@Override
	public T removeChild(final Long versionId, final T parent, final T child) {
		DataResponse<T> resp = getService().removeSub(versionId, parent.getId(), child.getId());
		return resp.getData();
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
