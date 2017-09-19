package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.lib.flux.EventType;
import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface ToolbarItem extends Serializable {

	String getDesc();

	String getImageUrl();

	EventType getEventType();

}
