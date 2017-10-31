package com.github.bordertech.wcomponents.lib.app.model;

import java.util.Collection;
import com.github.bordertech.flux.DataApi;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public interface RetrieveCollectionModel<S, T, C extends Collection<T>> extends DataApi {

	C retrieveCollection(final S criteria);

}
