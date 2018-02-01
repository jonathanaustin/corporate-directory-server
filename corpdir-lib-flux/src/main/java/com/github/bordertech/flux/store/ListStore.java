package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Store;
import java.util.List;

/**
 * Store that holds a list.
 *
 * @author Jonathan Austin
 * @param <T> the item type
 * @since 1.0.0
 */
public interface ListStore<T> extends Store {

	/**
	 * @return the list items
	 */
	List<T> getItems();

}
