package com.github.bordertech.corpdir.web.ui.flux;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.service.BasicIdService;
import com.github.bordertech.flux.crud.dataapi.CrudApi;

/**
 * CorpDir CRUD API with defined types.
 *
 * @author jonathan
 * @param <T> the CorpDir API Object
 * @param <S> the CorpDir backing Service
 */
public interface CorpCrudDataApi<T extends ApiIdObject, S extends BasicIdService<T>> extends CrudApi<String, String, T> {

}
