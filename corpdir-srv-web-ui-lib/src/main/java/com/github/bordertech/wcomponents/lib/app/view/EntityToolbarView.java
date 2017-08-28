package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;

/**
 * Entity action view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityToolbarView extends ToolbarView {

	EntityMode getEntityMode();

	void setEntityMode(final EntityMode mode);

	boolean isEntityLoaded();

	void setEntityLoaded(final boolean entityReady);

}
