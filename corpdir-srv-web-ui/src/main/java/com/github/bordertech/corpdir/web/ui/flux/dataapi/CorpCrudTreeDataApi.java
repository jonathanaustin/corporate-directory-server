package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.service.BasicTreeService;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;

/**
 * Corp CRUD Tree API with defined types.
 *
 * @author jonathan
 * @param <T> the Corp API Treeable Object
 * @param <S> the Corp backing Tree Service
 */
public interface CorpCrudTreeDataApi<T extends ApiTreeable, S extends BasicTreeService<T>> extends CorpCrudDataApi<T, S>, CrudTreeApi<String, String, T> {

}
