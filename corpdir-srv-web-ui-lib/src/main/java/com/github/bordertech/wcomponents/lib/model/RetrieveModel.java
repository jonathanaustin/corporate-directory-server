package com.github.bordertech.wcomponents.lib.model;

/**
 *
 * @author jonathan
 */
public interface RetrieveModel<K, T> extends Model {

	T retrieve(final K key);

}
