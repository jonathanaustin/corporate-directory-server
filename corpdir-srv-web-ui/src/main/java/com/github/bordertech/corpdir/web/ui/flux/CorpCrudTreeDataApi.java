package com.github.bordertech.corpdir.web.ui.flux;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.service.BasicTreeService;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;

/**
 * CorpDir CRUD Tree API with defined types.
 *
 * @author jonathan
 * @param <T> the CorpDir API Treeable Object
 * @param <S> the CorpDir backing Tree Service
 */
public interface CorpCrudTreeDataApi<T extends ApiTreeable, S extends BasicTreeService<T>> extends CorpCrudDataApi<T, S>, CrudTreeApi<String, String, T> {

}
