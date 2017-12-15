package com.github.bordertech.flux.wc.view.dumb.msg;

import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.wcomponents.WMessages;

/**
 * Default message view.
 *
 * @author jonathan
 */
public class DefaultMessageView<T> extends DefaultDumbView<T> implements MessageView<T> {

	private final WMessages messages;

	public DefaultMessageView(final String viewId) {
		this(viewId, true);
	}

	public DefaultMessageView(final String viewId, final boolean persistent) {
		super(viewId);
		messages = new WMessages(persistent);
		getContent().add(messages);
		messages.addHtmlClass("wc-margin-s-lg");
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
