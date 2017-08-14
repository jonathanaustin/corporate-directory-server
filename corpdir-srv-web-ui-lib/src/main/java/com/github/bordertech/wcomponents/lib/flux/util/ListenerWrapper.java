package com.github.bordertech.wcomponents.lib.flux.util;

import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class ListenerWrapper implements Serializable {

	private final String registerId = UUID.randomUUID().toString();
	private final EventMatcher matcher;
	private final Listener listener;

	public ListenerWrapper(final EventMatcher matcher, final Listener listener) {
		this.matcher = matcher;
		this.listener = listener;
	}

	public String getRegisterId() {
		return registerId;
	}

	public EventMatcher getMatcher() {
		return matcher;
	}

	public Listener getListener() {
		return listener;
	}

	@Override
	public int hashCode() {
		return registerId.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof ListenerWrapper && Objects.equals(((ListenerWrapper) obj).registerId, registerId);
	}

}
