package com.github.bordertech.corpdir.web.ui.dataapi;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicIdService;
import com.github.bordertech.didums.Didums;
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
public class DefaultCrudDataApi<T extends ApiIdObject, S extends BasicIdService<T>> implements CorpCrudApi<T, S> {

	private final Class<T> apiClass;
	private final S service;

	public DefaultCrudDataApi(final Class<T> apiClass, final Class<? extends S> serviceClass) {
		this.apiClass = apiClass;
		this.service = Didums.getService(serviceClass);
	}

	protected final S getService() {
		return service;
	}

	@Override
	public List<T> search(final String criteria) {
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
	public T retrieve(final String key) {
		DataResponse<T> resp = service.retrieve(key);
		return resp.getData();
	}

	@Override
	public String getItemKey(final T item) {
		return item.getId();
	}

	@Override
	public T createInstance() {
		try {
			return (T) apiClass.getConstructor(String.class).newInstance((Object) null);
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
			throw new IllegalStateException("Could not create API class. " + e.getMessage(), e);
		}
	}

}
