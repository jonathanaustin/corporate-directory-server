package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Store;
import java.util.List;

/**
 * Store that holds a list.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ListStore<T> extends Store {

	List<T> getItems();

}
