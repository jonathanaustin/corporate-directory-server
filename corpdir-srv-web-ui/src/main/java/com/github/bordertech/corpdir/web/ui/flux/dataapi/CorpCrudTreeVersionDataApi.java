package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;
import java.util.List;

/**
 * Corp CRUD Tree Version API with defined types.
 *
 * @author jonathan
 * @param <T> the Corp API Treeable &amp; Versionable Object
 * @param <S> the Corp backing Tree Service
 */
public interface CorpCrudTreeVersionDataApi<T extends ApiTreeable & ApiVersionable, S extends BasicVersionTreeService<T>> extends CorpCrudTreeDataApi<T, S>, CorpCrudVersionDataApi<T, S> {

	boolean hasChildren(final Long versionId, final T item);

	List<T> getChildren(final Long versionId, final T item);

	List<T> getRootItems(final Long versionId);

	T addChild(final Long versionId, final T parent, final T child);

	T removeChild(final Long versionId, final T parent, final T child);

}
