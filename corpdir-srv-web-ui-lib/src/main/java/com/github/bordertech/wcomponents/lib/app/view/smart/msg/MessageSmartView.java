package com.github.bordertech.wcomponents.lib.app.view.smart.msg;

import com.github.bordertech.flux.wc.view.ViewContainer;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.lib.app.view.MessageView;

/**
 * Smart view that is a Message Container.
 *
 * @author jonathan
 */
public interface MessageSmartView<T> extends ViewContainer<T>, MessageContainer {

	/**
	 *
	 * @return the message view
	 */
	MessageView getMessageView();
}
