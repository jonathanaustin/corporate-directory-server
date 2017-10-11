package com.github.bordertech.flux.impl;

/**
 *
 * @author jonathan
 */
public class UnregisterEvent extends DefaultEvent {

	private final String registerId;

	public UnregisterEvent(final String registerId) {
		super(DispatcherEventType.UNREGISTER);
		this.registerId = registerId;
	}

	public String getRegisterId() {
		return registerId;
	}

}
