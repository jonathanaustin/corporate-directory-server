package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Listeners are the callback used by the dispatcher when it receives a matching action.
 *
 * @param <T> the action type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Listener<T extends Action> extends Serializable {

	void handleAction(final T action);

}
