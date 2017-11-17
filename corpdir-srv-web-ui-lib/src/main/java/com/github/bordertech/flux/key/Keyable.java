package com.github.bordertech.flux.key;

import java.io.Serializable;

/**
 * Flux component is keyable.
 *
 * @param <T> the key type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Keyable<T extends Key> extends Serializable {

	T getKey();

}
