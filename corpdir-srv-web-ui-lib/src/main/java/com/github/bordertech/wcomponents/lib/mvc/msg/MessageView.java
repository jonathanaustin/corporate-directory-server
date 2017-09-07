package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Message view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean type
 * @since 1.0.0
 *
 */
public interface MessageView<T> extends View<T> {

	WMessages getMessages();

}
