package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultMessageComboView extends DefaultComboView implements MessageComboView {

	private final DefaultMessageCtrl messageCtrl = new DefaultMessageCtrl();
	private final MessageView messageView = new DefaultMessageView();

	public DefaultMessageComboView(final String templateName) {
		this(templateName, null);
	}

	public DefaultMessageComboView(final String templateName, final String qualifier) {
		super(templateName, qualifier);
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
