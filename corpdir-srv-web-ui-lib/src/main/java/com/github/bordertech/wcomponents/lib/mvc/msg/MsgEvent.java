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
		this.qualifier = new EventQualifier(MsgEventType.VALIDATION, qualifier);
		this.messages = Collections.EMPTY_LIST;
		this.encode = true;
		this.diags = diags;
	}

	public MsgEvent(final MsgEventType type, final String text) {
		this(type, null, text, true);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final String text) {
		this(type, qualifier, text, true);
	}

	public MsgEvent(final MsgEventType type, final String text, final boolean encode) {
		this(type, null, text, encode);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final String text, final boolean encode) {
		this(type, qualifier, text == null ? Collections.EMPTY_LIST : Arrays.asList(text), encode);
	}

	public MsgEvent(final MsgEventType type, final String qualifier, final List<String> messages, final boolean encode) {
		this.qualifier = new EventQualifier(type, qualifier);
		this.messages = messages == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(messages);
		this.encode = encode;
		this.diags = Collections.EMPTY_LIST;
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
