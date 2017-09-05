package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.lib.mvc.*;

/**
 * Is a combination of views (ie composite).
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface MessageComboView extends View {

	/**
	 *
	 * @return the combo view message controller
	 */
	DefaultMessageCtrl getMessageCtrl();

	/**
	 *
	 * @return the combo message view
	 */
	MessageView getMessageView();
}
