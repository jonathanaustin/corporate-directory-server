package com.github.bordertech.flux.dataapi.retrieve;

import com.github.bordertech.flux.dataapi.DataApi;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveApi<S, T> extends DataApi {

	T retrieve(final S criteria);

}
