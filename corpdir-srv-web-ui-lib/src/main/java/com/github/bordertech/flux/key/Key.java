package com.github.bordertech.flux.key;

import java.io.Serializable;

/**
 * Key used to identify flux components and actions.
 *
 * @param <T> the key type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Key<T extends KeyType> extends Serializable {

	T getType();

	String getQualifier();
}
