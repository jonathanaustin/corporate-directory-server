package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.key.EventKey;
import com.github.bordertech.flux.Listener;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ListenerWrapper implements Serializable {

	private final String registerId = UUID.randomUUID().toString();
	private final EventKey eventKey;
	private final Listener listener;

	public ListenerWrapper(final EventKey eventKey, final Listener listener) {
		this.eventKey = eventKey;
		this.listener = listener;
	}

	public String getRegisterId() {
		return registerId;
	}

	public EventKey getEventKey() {
		return eventKey;
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
