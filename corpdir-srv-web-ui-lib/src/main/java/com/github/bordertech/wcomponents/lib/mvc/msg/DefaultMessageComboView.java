package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultMessageComboView extends DefaultComboView implements MessageComboView {

	private final DefaultMessageCtrl messageCtrl;
	private final MessageView messageView;

	public DefaultMessageComboView(final String templateName, final Dispatcher dispatcher) {
		this(templateName, dispatcher, null);
	}

	public DefaultMessageComboView(final String templateName, final Dispatcher dispatcher, final String qualifier) {
		super(templateName, dispatcher, qualifier);
		this.messageCtrl = new DefaultMessageCtrl(dispatcher, qualifier);
		this.messageView = new DefaultMessageView(dispatcher, qualifier);
		messageCtrl.setMessageView(messageView);
		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", getMessageView());
		content.addTaggedComponent("vw-ctrl-msg", getMessageCtrl());
	}

	@Override
	public final DefaultMessageCtrl getMessageCtrl() {
		return messageCtrl;
	}

	@Override
	public final MessageView getMessageView() {
		return messageView;
	}

	@Override
	public void handleMessageEvent(final MsgEvent event) {
		// Check if any Controllers want to process the Message Event
		for (Controller ctrl : getControllers()) {
			if (ctrl instanceof DefaultMessageCtrl) {
				boolean processed = ((DefaultMessageCtrl) ctrl).handleMessageEvent(event);
				if (processed) {
					return;
				}
			}
		}
		// Try the parent
		MessageComboView parent = findComboMessageView();
		if (parent != null) {
			parent.handleMessageEvent(event);
		}
	}

}
