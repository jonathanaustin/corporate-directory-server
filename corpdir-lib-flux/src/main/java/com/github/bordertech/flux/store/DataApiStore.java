package com.github.bordertech.flux.store;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.dataapi.DataApiConsumer;

/**
 * Store that uses a data API to retrieve its state.
 *
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiStore<D extends DataApi> extends Store, DataApiConsumer<D> {

}
