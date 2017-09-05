package com.github.bordertech.wcomponents.lib.model;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface ServiceModel<S, T> extends Model {

	T service(final S criteria);

}
