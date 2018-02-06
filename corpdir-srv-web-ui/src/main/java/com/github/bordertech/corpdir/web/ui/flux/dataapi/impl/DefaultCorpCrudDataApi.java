package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicIdService;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudDataApi;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * CRUD API calling CorpDir Services.
 *
 * @param <T> the CorpDir API object type
 * @param <S> the CorpDir service type
 *
 * @author jonathan
 */
public class DefaultCorpCrudDataApi<T extends ApiIdObject, S extends BasicIdService<T>> implements CorpCrudDataApi<T, S> {

	private final Class<T> apiClass;
	private final S service;

	public DefaultCorpCrudDataApi(final Class<T> apiClass, final S service) {
		this.apiClass = apiClass;
		this.service = service;
	}

	@Override
	public final S getService() {
		return service;
	}

	@Override
	public final Class<T> getApiClass() {
		return apiClass;
	}

	@Override
	public List<T> search(final String criteria) {
		DataResponse<List<T>> resp = getService().search(criteria);
		return resp.getData();
	}

	@Override
	public T create(final T entity) {
		DataResponse<T> resp = getService().create(entity);
		return resp.getData();
	}

	@Override
	public T update(final T entity) {
		DataResponse<T> resp = getService().update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final T entity) {
		getService().delete(entity.getId());
	}

	@Override
	public T retrieve(final String key) {
		DataResponse<T> resp = getService().retrieve(key);
		return resp.getData();
	}

	@Override
	public String getItemKey(final T item) {
		return item.getId();
	}

	@Override
	public T createInstance() {
		try {
			return (T) getApiClass().getConstructor(String.class).newInstance((Object) null);
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
			throw new IllegalStateException("Could not create API class. " + e.getMessage(), e);
		}
	}

}
