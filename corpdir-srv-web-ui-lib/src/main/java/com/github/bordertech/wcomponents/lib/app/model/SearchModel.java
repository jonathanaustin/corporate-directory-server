package com.github.bordertech.wcomponents.lib.app.model;

import java.util.List;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface SearchModel<S, T> extends RetrieveCollectionModel<S, T, List<T>> {

}
