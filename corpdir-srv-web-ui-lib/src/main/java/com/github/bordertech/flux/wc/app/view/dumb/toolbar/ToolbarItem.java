package com.github.bordertech.flux.wc.app.view.dumb.toolbar;

import java.io.Serializable;
import com.github.bordertech.flux.wc.app.view.event.ToolbarEventType;

/**
 *
 * @author jonathan
 */
public interface ToolbarItem extends Serializable {

	String getDesc();

	String getImageUrl();

	ToolbarEventType getEventType();

}
