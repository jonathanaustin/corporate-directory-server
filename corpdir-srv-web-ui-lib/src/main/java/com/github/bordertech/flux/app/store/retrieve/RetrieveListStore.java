package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveItemApi;
import java.util.List;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <K> the item key type
 * @param <T> the item type
 */
public interface RetrieveListStore<K, T> extends RetrieveItemStore<K, List<T>>, RetrieveItemApi<K, List<T>> {

}
