package com.github.bordertech.wcomponents.lib.app.model;

import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface RetrieveModel<K, T> extends Serializable {

	T retrieve(final K key);

}
