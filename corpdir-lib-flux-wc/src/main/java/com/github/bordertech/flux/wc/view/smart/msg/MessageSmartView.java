package com.github.bordertech.flux.wc.view.smart.msg;

import com.github.bordertech.flux.wc.view.FluxSmartView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;

/**
 * Smart view that is a Message Container.
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface MessageSmartView<T> extends FluxSmartView<T>, MessageView<T> {

	/**
	 *
	 * @return the message view
	 */
	MessageView<T> getMessageView();
}
