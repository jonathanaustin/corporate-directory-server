package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.flux.dataapi.retrieve.RetrieveListApi;
import java.util.List;

/**
 * Store that holds list values.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public interface RetrieveListStore<S, T> extends RetrieveStore<S, List<T>>, RetrieveListApi<S, T> {

}
