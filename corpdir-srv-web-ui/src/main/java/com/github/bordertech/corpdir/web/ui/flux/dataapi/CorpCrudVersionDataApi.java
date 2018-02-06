package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.service.BasicVersionKeyIdService;
import java.util.List;

/**
 * Corp CRUD Version API with defined types.
 *
 * @author jonathan
 * @param <T> the Corp API Versionable Object
 * @param <S> the Corp backing Service
 */
public interface CorpCrudVersionDataApi<T extends ApiVersionable, S extends BasicVersionKeyIdService<T>> extends CorpCrudDataApi<T, S> {

	T retrieve(final Long versionId, final String key);

	List<T> search(final Long versionId, final String criteria);

}
