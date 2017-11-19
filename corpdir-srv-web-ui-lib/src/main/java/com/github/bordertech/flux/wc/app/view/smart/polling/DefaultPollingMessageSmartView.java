package com.github.bordertech.flux.wc.app.view.smart.polling;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.view.MessageView;
import com.github.bordertech.flux.wc.app.view.dumb.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseEventType;
import com.github.bordertech.flux.wc.app.view.event.util.MessageEventUtil;
import com.github.bordertech.flux.wc.app.view.smart.MessageSmartView;
import com.github.bordertech.wcomponents.WMessages;

/**
 * Default Smart View that is a Message Container and has a polling view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultPollingMessageSmartView<T> extends DefaultPollingSmartView<T> implements MessageSmartView<T> {

	private final MessageView messageView = new DefaultMessageView("vw_msgs");

	public DefaultPollingMessageSmartView(final String viewId, final String templateName) {
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
		if (event instanceof MessageBaseEventType) {
			handleMesssageEvent(viewId, (MessageBaseEventType) event, data);
		}
	}

	protected void handleMesssageEvent(final String viewId, final MessageBaseEventType event, final Object data) {
		MessageEventUtil.handleMessageBaseViewEvents(getMessageView(), event, data);
	}
}
