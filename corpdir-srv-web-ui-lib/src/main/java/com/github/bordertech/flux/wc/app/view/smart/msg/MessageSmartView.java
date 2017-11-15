package com.github.bordertech.flux.wc.app.view.smart.msg;

import com.github.bordertech.flux.wc.app.view.MessageView;
import com.github.bordertech.flux.wc.view.SmartView;
import com.github.bordertech.wcomponents.MessageContainer;

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
