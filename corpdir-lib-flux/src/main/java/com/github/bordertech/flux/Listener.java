package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * @param <T> the action type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Listener<T extends Action> extends Serializable {

	void handleAction(final T action);

}
