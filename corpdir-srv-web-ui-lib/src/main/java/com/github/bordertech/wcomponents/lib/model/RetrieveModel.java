package com.github.bordertech.wcomponents.lib.model;

/**
 * Retrieve a data type.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveModel<S, T> extends Model {

	T retrieve(final S key);

}
