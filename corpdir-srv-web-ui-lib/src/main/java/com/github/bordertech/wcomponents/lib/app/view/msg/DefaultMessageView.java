package com.github.bordertech.wcomponents.lib.app.view.msg;

import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.view.MessageView;

/**
 * Default message view.
 *
 * @author jonathan
 */
public class DefaultMessageView<T> extends DefaultDumbView<T> implements MessageView<T> {

	private final WMessages messages = new WMessages(true);

	public DefaultMessageView(final String viewId) {
		super(viewId);
		getContent().add(messages);
		messages.addHtmlClass("wc-margin-s-lg");
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
