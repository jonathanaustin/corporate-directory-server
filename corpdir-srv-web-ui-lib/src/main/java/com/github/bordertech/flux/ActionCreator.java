package com.github.bordertech.flux;

import com.github.bordertech.flux.key.CreatorKey;
import com.github.bordertech.flux.key.Keyable;

/**
 * Provides the action (event) creator interface to handle change store events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ActionCreator extends Keyable<CreatorKey> {

}
