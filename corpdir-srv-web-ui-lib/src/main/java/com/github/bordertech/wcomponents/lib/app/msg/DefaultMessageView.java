package com.github.bordertech.wcomponents.lib.app.msg;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.flux.wc.view.DumbView;

/**
 * Default message view.
 *
 * @author jonathan
 */
public class DefaultMessageView<T> extends DumbView<T> implements MessageView<T> {

	private final WMessages messages = new WMessages(true);

	public DefaultMessageView() {
		getContent().add(messages);
		messages.addHtmlClass("wc-margin-s-lg");
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
