package com.github.bordertech.wcomponents.lib.app.view.smart.msg;

import com.github.bordertech.flux.wc.view.SmartView;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.view.MessageView;
import com.github.bordertech.wcomponents.lib.app.view.msg.DefaultMessageView;

/**
 * Default Smart View that is a Message Container.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultMessageSmartView<T> extends SmartView<T> implements MessageSmartView<T> {

	private final MessageView messageView;

	public DefaultMessageSmartView(final String templateName) {
		super(templateName);
		messageView = new DefaultMessageView();
		addComponentToContentTemplate("vw-messages", messageView);
	}

	@Override
	public final MessageView getMessageView() {
		return messageView;
	}

	@Override
	public WMessages getMessages() {
		return messageView.getMessages();
	}

}
