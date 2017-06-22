package com.github.bordertech.wcomponents.lib.pub;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Subscriber extends WComponent {

	List<AjaxTarget> getEventAjaxTargets(final Class<? extends Event> event);

	void handleEvent(final Event event);

}
