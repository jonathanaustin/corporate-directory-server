package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.impl.BasicView;

/**
 * Entity action view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityActionView extends BasicView {

	EntityMode getEntityMode();

	void setEntityMode(final EntityMode mode);

	boolean isEntityLoaded();

	void setEntityLoaded(final boolean entityReady);

	boolean isUseBack();

	void setUseBack(final boolean useBack);

}
