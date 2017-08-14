package com.github.bordertech.wcomponents.lib.flux.util;

import com.github.bordertech.wcomponents.lib.flux.Event;

/**
 *
 * @author jonathan
 */
public class RegisterEvent extends Event<ListenerWrapper> {

	public RegisterEvent(final ListenerWrapper wrapper) {
		super(null, DispatcherEventType.REGISTER, wrapper);
	}

	public ListenerWrapper getWrapper() {
		return getData();
	}

}
