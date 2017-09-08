package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultMessageComboView<T> extends DefaultComboView<T> implements MessageComboView<T> {

	private final DefaultMessageCtrl messageCtrl;
	private final MessageView messageView;

	public DefaultMessageComboView(final String templateName) {
		super(templateName);
		messageCtrl = new DefaultMessageCtrl();
		messageView = new DefaultMessageView();
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

}
