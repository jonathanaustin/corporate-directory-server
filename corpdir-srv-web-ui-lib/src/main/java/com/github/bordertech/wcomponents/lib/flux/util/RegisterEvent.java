package com.github.bordertech.wcomponents.lib.flux.util;

import com.github.bordertech.wcomponents.lib.flux.Event;

/**
 *
 * @author jonathan
 */
public class RegisterEvent extends Event {

	private final ListenerWrapper wrapper;

	public RegisterEvent(final ListenerWrapper wrapper) {
		super(DispatcherEventType.REGISTER);
		this.wrapper = wrapper;
	}

	public ListenerWrapper getWrapper() {
		return wrapper;
	}

}
