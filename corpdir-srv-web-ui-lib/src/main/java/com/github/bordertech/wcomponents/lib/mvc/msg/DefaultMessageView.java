package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 * Default message view.
 *
 * @author jonathan
 */
public class DefaultMessageView<T> extends DefaultView<T> implements MessageView<T> {

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
