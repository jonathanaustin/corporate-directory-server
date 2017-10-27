package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.wcomponents.lib.mvc.Model;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveModel<S, T> extends Model {

	T retrieve(final S criteria);

}
