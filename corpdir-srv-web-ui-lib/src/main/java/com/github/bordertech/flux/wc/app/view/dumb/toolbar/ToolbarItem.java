package com.github.bordertech.flux.wc.app.view.dumb.toolbar;

import com.github.bordertech.flux.wc.app.view.event.ToolbarViewEvent;
import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface ToolbarItem extends Serializable {

	String getDesc();

	String getImageUrl();

	ToolbarViewEvent getEventType();

}
