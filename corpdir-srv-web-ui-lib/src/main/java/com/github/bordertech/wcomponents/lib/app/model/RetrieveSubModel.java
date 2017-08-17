package com.github.bordertech.wcomponents.lib.app.model;

import java.util.List;

/**
 *
 * @author jonathan
 */
public interface RetrieveSubModel<K, T> extends RetrieveModel<K, T> {

	List<T> retrieveSubs(final K key);

}
