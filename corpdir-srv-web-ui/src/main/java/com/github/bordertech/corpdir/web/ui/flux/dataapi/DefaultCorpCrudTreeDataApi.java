package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicTreeService;
import java.util.List;

/**
 * CRUD Tree API calling CorpDir Services.
 *
 * @param <T> the CorpDir Treeable API object
 * @param <S> the CorpDir tree service type
 *
 * @author jonathan
 */
public class DefaultCorpCrudTreeDataApi<T extends ApiTreeable, S extends BasicTreeService<T>> extends DefaultCorpCrudDataApi<T, S> implements CorpCrudTreeDataApi<T, S> {

	public DefaultCorpCrudTreeDataApi(final Class<T> apiClass, final S service) {
		super(apiClass, service);
	}

	@Override
	public List<T> search(final String criteria) {
		DataResponse<List<T>> resp = getService().search(criteria);
		return resp.getData();
	}

	@Override
	public List<T> getChildren(final T entity) {
		DataResponse<List<T>> resp = getService().getSubs(entity.getId());
		return resp.getData();
	}

	@Override
	public boolean hasChildren(final T entity) {
		return !entity.getSubIds().isEmpty();
	}

	@Override
	public String getItemLabel(final T entity) {
		return entity.getDescription() + " [" + entity.getBusinessKey() + "]";
	}

	@Override
	public String getItemId(final T entity) {
		return entity.getId();
	}

	@Override
	public List<T> getRootItems() {
		DataResponse<List<T>> resp = getService().getRootItems();
		return resp.getData();
	}

	@Override
	public T addChild(final T parent, final T child) {
		DataResponse<T> resp = getService().addSub(parent.getId(), child.getId());
		return resp.getData();
	}

	@Override
	public T removeChild(final T parent, final T child) {
		DataResponse<T> resp = getService().removeSub(parent.getId(), child.getId());
		return resp.getData();
	}

}
