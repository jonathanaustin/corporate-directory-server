package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Store;
import java.util.List;

/**
 * Store that holds a list.
 *
 * @author jonathan
 */
public interface ListStore<T> extends Store {

	List<T> getItems();

}
