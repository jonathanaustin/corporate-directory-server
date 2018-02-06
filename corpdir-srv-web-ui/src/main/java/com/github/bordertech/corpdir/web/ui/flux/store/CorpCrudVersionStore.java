package com.github.bordertech.corpdir.web.ui.flux.store;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudVersionDataApi;

/**
 * Corp CRUD Store with backing data API.
 *
 * @author jonathan
 * @param <T> the Corp API Object
 * @param <D> the Corp data API type
 */
public interface CorpCrudVersionStore<T extends ApiVersionable, D extends CorpCrudVersionDataApi<T, ?>>
		extends CorpCrudStore<T, D> {

	/**
	 * @return the current version id
	 */
	Long getCurrentVersionId();

}
