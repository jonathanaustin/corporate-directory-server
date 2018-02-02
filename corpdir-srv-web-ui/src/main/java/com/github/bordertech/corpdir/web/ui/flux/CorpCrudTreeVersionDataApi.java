package com.github.bordertech.corpdir.web.ui.flux;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;

/**
 * CorpDir CRUD Tree version API with defined types.
 *
 * @author jonathan
 * @param <T> the CorpDir API Treeable Object
 * @param <S> the CorpDir backing Tree Service
 */
public interface CorpCrudTreeVersionDataApi<T extends ApiTreeable & ApiVersionable, S extends BasicVersionTreeService<T>> extends CorpCrudTreeDataApi<T, S> {

}
