package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.flux.wc.DataApi;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveModel<S, T> extends DataApi {

	T retrieve(final S criteria);

}
