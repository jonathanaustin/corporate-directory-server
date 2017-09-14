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
public class MsgEvent implements Event {

	private final Qualifier qualifier;

	private final List<String> messages;

	private final boolean encode;

	private final List<Diagnostic> diags;

	public MsgEvent(final List<Diagnostic> diags) {
		this(diags, null);
	}

	public MsgEvent(final List<Diagnostic> diags, final String qualifier) {
		this(MsgEventType.VALIDATION, qualifier, Collections.EMPTY_LIST, true, diags);
	}

	public MsgEvent(final MsgEventType type, final String message) {
		this(type, null, message, true);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final String message) {
		this(type, qualifier, message, true);
	}

	public MsgEvent(final MsgEventType type, final String message, final boolean encode) {
		this(type, null, message, encode);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final String message, final boolean encode) {
		this(type, qualifier, message == null ? Collections.EMPTY_LIST : Arrays.asList(message), encode);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final List<String> messages, final boolean encode) {
		this(type, qualifier, messages, encode, Collections.EMPTY_LIST);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final List<String> messages, final boolean encode, final List<Diagnostic> diags) {
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
