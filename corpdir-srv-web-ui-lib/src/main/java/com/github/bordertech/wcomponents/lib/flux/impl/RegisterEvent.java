package com.github.bordertech.wcomponents.lib.flux.impl;

/**
 *
 * @author jonathan
 */
public class RegisterEvent extends DefaultEvent {

	private final ListenerWrapper wrapper;

	public RegisterEvent(final ListenerWrapper wrapper) {
		super(DispatcherEventType.REGISTER);
		this.wrapper = wrapper;
	}

	public ListenerWrapper getWrapper() {
		return wrapper;
	}

}
