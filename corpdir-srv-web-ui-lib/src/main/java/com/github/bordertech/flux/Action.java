package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.Keyable;

/**
 * Flux Action between a view and a store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Action extends Keyable<ActionKey> {

	Object getData();

}
