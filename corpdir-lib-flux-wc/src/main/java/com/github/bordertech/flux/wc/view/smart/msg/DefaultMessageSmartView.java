package com.github.bordertech.flux.wc.view.smart.msg;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.view.event.base.MessageBaseEventType;
import com.github.bordertech.flux.wc.view.event.util.MessageEventUtil;
import com.github.bordertech.flux.wc.view.smart.MessageSmartView;
import com.github.bordertech.wcomponents.WMessages;

/**
 * Default Smart View that is a Message Container.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultMessageSmartView<T> extends DefaultSmartView<T> implements MessageSmartView<T> {

	private final MessageView messageView = new DefaultMessageView("vw_msgs");

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
