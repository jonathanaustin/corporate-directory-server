package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.flux.impl.WView;

/**
 * Entity action view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityActionView extends WView {

	EntityMode getEntityMode();

	void setEntityMode(final EntityMode mode);

	boolean isEntityLoaded();

	void setEntityLoaded(final boolean entityReady);

	boolean isUseBack();

	void setUseBack(final boolean useBack);

}
