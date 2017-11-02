package com.github.bordertech.wcomponents.lib.app.view.smart.msg;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.lib.app.view.MessageView;
import com.github.bordertech.flux.wc.view.SmartView;

/**
 * Smart view that is a Message Container.
 *
 * @author jonathan
 */
public interface MessageSmartView<T> extends SmartView<T>, MessageContainer {

	/**
	 *
	 * @return the message view
	 */
	MessageView getMessageView();
}
