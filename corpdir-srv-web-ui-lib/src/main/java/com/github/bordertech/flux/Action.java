package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionKey;

/**
 * Flux Action between a view and a store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Action {

	ActionKey getKey();

	Object getData();

}
