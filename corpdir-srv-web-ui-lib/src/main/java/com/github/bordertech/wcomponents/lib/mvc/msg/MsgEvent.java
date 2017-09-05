package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class MsgEvent implements Serializable {

	private final MsgEventType type;

	private final List<String> messages;

	private final boolean encode;

	private final List<Diagnostic> diags;

	public MsgEvent(final List<Diagnostic> diags) {
		this.type = MsgEventType.VALIDATION;
		this.messages = Collections.EMPTY_LIST;
		this.encode = true;
		this.diags = diags;
	}

	public MsgEvent(final MsgEventType type, final String text) {
		this(type, text, true);
	}

	public MsgEvent(final MsgEventType type, final String text, final boolean encode) {
		this(type, text == null ? Collections.EMPTY_LIST : Arrays.asList(text), encode);
	}

	public MsgEvent(final MsgEventType type, final List<String> messages, final boolean encode) {
		this.type = type;
		this.messages = messages == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(messages);
		this.encode = encode;
		this.diags = Collections.EMPTY_LIST;
	}

	public MsgEventType getType() {
		return type;
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

}
