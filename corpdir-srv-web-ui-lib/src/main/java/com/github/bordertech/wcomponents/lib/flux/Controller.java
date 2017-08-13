package com.github.bordertech.wcomponents.lib.flux;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WMessages;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Controller extends Serializable {

	/**
	 *
	 * @return the dispatcher attached to this controller.
	 */
	Dispatcher getDispatcher();

	WMessages getViewMessages();

	List<AjaxTarget> getEventTargets(final View view, final EventType eventType);

}
