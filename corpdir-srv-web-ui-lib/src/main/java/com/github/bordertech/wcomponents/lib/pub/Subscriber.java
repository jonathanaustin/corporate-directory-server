package com.github.bordertech.wcomponents.lib.pub;

import com.github.bordertech.wcomponents.AjaxTarget;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Subscriber extends Serializable {

	List<AjaxTarget> getEventTargets(final Class<? extends Event> event);

	void handleEvent(final Event event);

}
