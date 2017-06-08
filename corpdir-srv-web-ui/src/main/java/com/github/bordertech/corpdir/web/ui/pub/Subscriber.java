package com.github.bordertech.corpdir.web.ui.pub;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Subscriber extends WComponent {

	List<AjaxTarget> getSubscriberAjaxTargets(final Class<? extends Event> event);

	void handleEvent(final Event event);

}
