package com.github.bordertech.flux.wc.view.smart.polling;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.view.event.base.MessageBaseEventType;
import com.github.bordertech.flux.wc.view.event.util.MessageEventUtil;
import com.github.bordertech.flux.wc.view.smart.MessageSmartView;
import com.github.bordertech.wcomponents.WMessages;

/**
 * Default Smart View that is a Message Container and has a polling view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultPollingMessageSmartView<T> extends DefaultPollingSmartView<T> implements MessageSmartView<T> {

	private final MessageView messageView;

	public DefaultPollingMessageSmartView(final String viewId, final String templateName) {
		this(viewId, templateName, true);
	}

	public DefaultPollingMessageSmartView(final String viewId, final String templateName, final boolean persistent) {
		super(viewId, templateName);
		messageView = new DefaultMessageView("vw_msgs", persistent);
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
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
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
