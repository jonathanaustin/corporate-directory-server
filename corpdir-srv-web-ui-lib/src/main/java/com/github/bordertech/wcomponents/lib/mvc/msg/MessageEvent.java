package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Qualifier;
import com.github.bordertech.wcomponents.lib.flux.impl.EventQualifier;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class MessageEvent implements Event {

	private final Qualifier qualifier;

	private final List<String> messages;

	private final boolean encode;

	private final List<Diagnostic> diags;

	public MessageEvent(final List<Diagnostic> diags) {
		this(diags, null);
	}

	public MessageEvent(final List<Diagnostic> diags, final String qualifier) {
		this(MessageEventType.VALIDATION, qualifier, Collections.EMPTY_LIST, true, diags);
	}

	public MessageEvent(final MessageEventType type, final String message) {
		this(type, null, message, true);
	}

	public MessageEvent(final MessageEventType type, final String qualifier, final String message) {
		this(type, qualifier, message, true);
	}

	public MessageEvent(final MessageEventType type, final String message, final boolean encode) {
		this(type, null, message, encode);
	}

	public MessageEvent(final MessageEventType type, final String qualifier, final String message, final boolean encode) {
		this(type, qualifier, message == null ? Collections.EMPTY_LIST : Arrays.asList(message), encode);
	}

	public MessageEvent(final MessageEventType type, final String qualifier, final List<String> messages, final boolean encode) {
		this(type, qualifier, messages, encode, Collections.EMPTY_LIST);
	}

	public MessageEvent(final MessageEventType type, final String qualifier, final List<String> messages, final boolean encode, final List<Diagnostic> diags) {
		this.qualifier = new EventQualifier(type, qualifier);
		this.messages = messages == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(messages);
		this.encode = encode;
		this.diags = diags;
	}

	public List<String> getMessages() {
		return messages;
	}

	public boolean isEncode() {
		return encode;
	}

	public List<Diagnostic> getDiags() {
		return diags;
	}

	@Override
	public Qualifier getQualifier() {
		return qualifier;
	}

	@Override
	public Object getData() {
		return null;
	}

	@Override
	public Exception getException() {
		return null;
	}

}
