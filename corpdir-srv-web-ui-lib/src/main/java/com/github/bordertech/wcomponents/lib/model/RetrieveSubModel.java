package com.github.bordertech.wcomponents.lib.model;

import java.util.List;

/**
 * Retrieve data types with sub data.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveSubModel<S, T> extends RetrieveModel<S, T> {

	List<T> retrieveSubs(final S key);

}
