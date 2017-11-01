package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.flux.event.ViewEventType;
import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface ToolbarItem extends Serializable {

	String getDesc();

	String getImageUrl();

	ViewEventType getEventType();

}
