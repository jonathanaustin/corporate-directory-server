package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Message view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface MsgView extends View {

	WMessages getMessages();

}
