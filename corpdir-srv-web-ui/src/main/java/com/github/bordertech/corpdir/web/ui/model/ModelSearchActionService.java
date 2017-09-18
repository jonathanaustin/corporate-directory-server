package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicService;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.app.model.ActionModel;
import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import java.util.List;

/**
 * API Search and Action Model.
 *
 * @param <T> the API object type
 *
 * @author jonathan
 */
public class ModelSearchActionService<T extends ApiKeyIdObject> implements SearchModel<String, T>, ActionModel<T> {

	private final Class<T> apiClass;
	private final BasicService<T> service;

	public ModelSearchActionService(final Class<T> apiClass, final Class<? extends BasicService<T>> serviceClass) {
		this.apiClass = apiClass;
		this.service = LocatorUtil.getService(serviceClass);
	}

	@Override
	public List<T> retrieveCollection(final String criteria) {
		DataResponse<List<T>> resp = service.search(criteria);
		return resp.getData();
	}

	@Override
	public T create(final T entity) {
		DataResponse<T> resp = service.create(entity);
		return resp.getData();
	}

	@Override
	public T update(final T entity) {
		DataResponse<T> resp = service.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final T entity) {
		service.delete(entity.getId());
	}

	@Override
	public T refresh(final T entity) {
		DataResponse<T> resp = service.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public T createInstance() {
		try {
			return (T) apiClass.getConstructor(String.class).newInstance((Object) null);
		} catch (Exception e) {
			throw new IllegalStateException("Could not create API class. " + e.getMessage(), e);
		}
	}

}
