package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Dispatcher extends Serializable {

	void dispatch(final Event event);

	List<Listener> getListeners();

	List<Listener> getListeners(final EventType eventType);

	void addListener(final Listener listener);

	void removeListener(final Listener listener);

}
