package com.github.bordertech.wcomponents.lib.flux.util;

import com.github.bordertech.wcomponents.lib.flux.Event;

/**
 *
 * @author jonathan
 */
public class UnregisterEvent extends Event {

	private final String registerId;

	public UnregisterEvent(final String registerId) {
		super(DispatcherEventType.UNREGISTER);
		this.registerId = registerId;
	}

	public String getRegisterId() {
		return registerId;
	}

}
