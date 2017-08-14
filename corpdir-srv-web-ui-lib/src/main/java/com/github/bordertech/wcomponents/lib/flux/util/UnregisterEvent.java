package com.github.bordertech.wcomponents.lib.flux.util;

import com.github.bordertech.wcomponents.lib.flux.Event;

/**
 *
 * @author jonathan
 */
public class UnregisterEvent extends Event<String> {

	public UnregisterEvent(final String registerId) {
		super(null, DispatcherEventType.UNREGISTER, registerId);
	}

	public String getRegisterId() {
		return getData();
	}

}
