package com.github.bordertech.flux.wc.app.view.smart.msg;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.view.MessageView;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseEventUtil;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.wcomponents.WMessages;

/**
 * Default Smart View that is a Message Container.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultMessageSmartView<T> extends DefaultSmartView<T> implements MessageSmartView<T> {

	private final MessageView messageView = new DefaultMessageView("vw-messages");

	public DefaultMessageSmartView(final String viewId, final String templateName) {
		super(viewId, templateName);
		addComponentToTemplate("vw-messages", messageView);
	}

	@Override
	public final MessageView getMessageView() {
		return messageView;
	}

	@Override
	public WMessages getMessages() {
		return messageView.getMessages();
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		// Message events
		if (event instanceof MessageBaseViewEvent) {
			handleMesssageEvent(viewId, (MessageBaseViewEvent) event, data);
		}
	}

	protected void handleMesssageEvent(final String viewId, final MessageBaseViewEvent event, final Object data) {
		MessageBaseEventUtil.handleMessageEvent(getMessages(), event, data);
	}

}
