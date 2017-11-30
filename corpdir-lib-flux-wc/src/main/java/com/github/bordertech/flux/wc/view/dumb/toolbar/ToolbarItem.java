package com.github.bordertech.flux.wc.view.dumb.toolbar;

import com.github.bordertech.flux.wc.view.event.ToolbarEventType;
import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface ToolbarItem extends Serializable {

	String getDesc();

	String getImageUrl();

	ToolbarEventType getEventType();

}
