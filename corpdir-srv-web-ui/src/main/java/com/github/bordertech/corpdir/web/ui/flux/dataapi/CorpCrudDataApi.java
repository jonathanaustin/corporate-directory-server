package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.service.BasicIdService;
import com.github.bordertech.flux.crud.dataapi.CrudApi;

/**
 * Corp CRUD API with defined types.
 *
 * @author jonathan
 * @param <T> the Corp API Object
 * @param <S> the Corp backing Service
 */
public interface CorpCrudDataApi<T extends ApiIdObject, S extends BasicIdService<T>> extends CrudApi<String, String, T> {

	Class<T> getApiClass();

	S getService();

}
